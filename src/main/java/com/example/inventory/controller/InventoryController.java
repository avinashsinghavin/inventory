package com.example.inventory.controller;

import com.example.inventory.request.RequestFilterRequest;
import com.example.inventory.request.ProductsSearchRequest;
import com.example.inventory.response.DefaultResponse;
import com.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.example.inventory.constant.ConstantValue.FILE_REQUIRED;


@RestController
@Validated
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/upload/csv")
    public ResponseEntity<?> saveCSV(@RequestParam("csv") MultipartFile request) {
        if (request == null || request.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DefaultResponse.builder()
                    .message(FILE_REQUIRED).build());

        return inventoryService.saveInventory(request);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterProduct(@Valid @RequestBody RequestFilterRequest requestFilterRequest) {
        return inventoryService.getFilteredData(requestFilterRequest);
    }

    @PostMapping("/allProducts")
    public ResponseEntity<?> filterProduct(@Valid @RequestBody ProductsSearchRequest request) {
        return inventoryService.filterProduct(request);
    }
}
