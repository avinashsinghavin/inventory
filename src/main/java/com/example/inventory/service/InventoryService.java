package com.example.inventory.service;

import com.example.inventory.request.RequestFilterRequest;
import com.example.inventory.request.ProductsSearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface InventoryService {
    ResponseEntity<?> saveInventory(MultipartFile file);

    ResponseEntity<?> getFilteredData(RequestFilterRequest requestFilterRequest);

    ResponseEntity<?> filterProduct(ProductsSearchRequest request);
}
