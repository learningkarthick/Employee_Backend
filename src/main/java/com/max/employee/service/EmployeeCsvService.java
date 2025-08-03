package com.max.employee.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.max.employee.model.Employee;
import com.max.employee.repository.EmployeeRepository;
import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@Service
public class EmployeeCsvService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private BlobContainerClient containerClient;

    @Value("${azure.storage.connection-string}")
    private String connectionString;
    @Value("${azure.storage.container-name}")
    private String containerName;



    @PostConstruct
    public void init() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }


    public String generateAndUploadCsv() throws IOException {
        // 1. Fetch data from DB
        List<Employee> employees = employeeRepository.findAll();

        // 2. Create temp CSV file
        File tempFile = File.createTempFile("employees-", ".csv");
        try (FileWriter fileWriter = new FileWriter(tempFile); CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            // Write header
            csvWriter.writeNext(new String[]{"ID", "Name", "Email", "Department"});

            // Write rows
            for (Employee emp : employees) {
                csvWriter.writeNext(new String[]{String.valueOf(emp.getId()), emp.getName(), emp.getEmail(), emp.getDepartment()});
            }
        }
// 3. Upload to Azure Blob
        String blobName = "reports/employees-" + System.currentTimeMillis() + ".csv";
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        try (InputStream inputStream = Files.newInputStream(tempFile.toPath())) {
            blobClient.upload(inputStream, tempFile.length(), true);
        }

        // 4. Delete temp file
        tempFile.delete();

        return blobName; // Return the blob path
    }
}