package com.example.inventory.mapper;

import com.example.inventory.entity.InventoryEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Component
public class ConvertFile {
    public List<InventoryEntity> csvToInventoryEntity(InputStream request1) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(request1, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<InventoryEntity> inventoryList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                InventoryEntity inventory = new InventoryEntity();
                inventory.setCode(csvRecord.get("code"));
                inventory.setName(csvRecord.get("name"));
                inventory.setBatch(csvRecord.get("batch"));
                inventory.setStock(Long.valueOf(csvRecord.get("stock")));
                inventory.setDeal(Integer.valueOf(csvRecord.get("deal")));
                inventory.setFree(Integer.valueOf(csvRecord.get("free")));
                inventory.setMrp(Double.valueOf(csvRecord.get("mrp")));
                inventory.setRate(Double.valueOf(csvRecord.get("rate")));
                inventory.setExp(csvRecord.get("exp"));
                inventory.setCompany(csvRecord.get("company"));
                inventory.setSupplier(csvRecord.get("supplier"));
                inventoryList.add(inventory);
            }
            return inventoryList;
        } catch (IOException e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }
}
