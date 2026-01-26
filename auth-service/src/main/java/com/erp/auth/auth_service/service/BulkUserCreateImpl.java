package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.dto.BulkResponse;
import com.erp.auth.auth_service.entity.AuthUser;
import com.erp.auth.auth_service.entity.Role;
import com.erp.auth.auth_service.entity.Status;
import com.erp.auth.auth_service.repository.AuthUserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BulkUserCreateImpl implements BulkUserCreate{
    private AuthUserRepository authUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private int TOTAL_RECORDS;
    private int INSERTED;
    private int SKIPPED;

    public BulkUserCreateImpl(AuthUserRepository authUserRepository,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.authUserRepository=authUserRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public BulkResponse bulkProvisionUsers(MultipartFile file) {

        List<String> errors = new ArrayList<>();
        List<BulkUserCreateImpl.CsvUserRow> rows = parseCsv(file, errors);

        for (BulkUserCreateImpl.CsvUserRow row : rows) {

            try {
                // 1️⃣ Validate role
                Role role;
                try {
                    role = Role.valueOf(row.role.toUpperCase());
                } catch (IllegalArgumentException e) {
                    errors.add("Row " + row.rowNumber + ": invalid role");
                    SKIPPED++;
                    continue;
                }

                // 2️⃣ Check duplicate username
                if (authUserRepository.findByUsername(row.username).isPresent()) {
                    errors.add("Row " + row.rowNumber + ": username already exists");
                    SKIPPED++;
                    continue;
                }

                // 3️⃣ Hash password
                String hashedPassword =
                        bCryptPasswordEncoder.encode(row.password);

                // 4️⃣ Create AuthUser
                AuthUser user = new AuthUser();
                user.setUsername(row.username);
                user.setPasswordHash(hashedPassword);
                user.setRole(role);
                user.setStatus(Status.ACTIVE);
                user.setCreatedAt(Instant.now());

                // 5️⃣ Save user
                authUserRepository.save(user);
                INSERTED++;

            } catch (Exception e) {
                errors.add("Row " + row.rowNumber + ": unexpected error");
                SKIPPED++;
            }
        }

        BulkResponse res= new BulkResponse(
                "true",
                TOTAL_RECORDS,
                INSERTED,
                SKIPPED,
                errors
        );
        TOTAL_RECORDS=0;
        INSERTED=0;
        SKIPPED=0;
        return res;
    }
    private List<BulkUserCreateImpl.CsvUserRow> parseCsv(MultipartFile file, List<String> errors) {

        List<BulkUserCreateImpl.CsvUserRow> rows = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)
                );
                CSVParser csvParser = new CSVParser(
                        reader,
                        CSVFormat.DEFAULT
                                .withFirstRecordAsHeader()
                                .withIgnoreHeaderCase()
                                .withTrim()
                )
        ) {
            int rowNumber = 1; // data row count (excluding header)

            for (CSVRecord record : csvParser) {
                rowNumber++;

                try {
                    String username = record.get("username");
                    String role = record.get("role");
                    String password = record.get("password");

                    if (username.isBlank() || role.isBlank() || password.isBlank()) {
                        errors.add("Row " + rowNumber + ": empty field detected");
                        SKIPPED++;
                        continue;
                    }

                    rows.add(new BulkUserCreateImpl.CsvUserRow(username, role, password, rowNumber));

                } catch (IllegalArgumentException e) {
                    errors.add("Row " + rowNumber + ": invalid column format");
                    SKIPPED++;
                }
            }
          TOTAL_RECORDS = rowNumber-1;
        } catch (Exception e) {
            errors.add("Failed to read CSV file: " + e.getMessage());
        }

        return rows;
    }
    private static class CsvUserRow
    {
        String username;
        String role;
        String password;
        int rowNumber;

        public CsvUserRow(String username, String role, String password,int rowNumber)
        {
            this.username=username;
            this.role=role;
            this.password=password;
            this.rowNumber=rowNumber;
        }
    }
}
