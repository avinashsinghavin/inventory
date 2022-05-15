package com.example.inventory.service;

import com.example.inventory.entity.InventoryEntity;
import com.example.inventory.mapper.ConvertFile;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.request.ProductsSearchRequest;
import com.example.inventory.request.RequestFilterRequest;
import com.example.inventory.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.inventory.constant.ConstantValue.*;


@Service
public class InventoryServiceImpl implements InventoryService {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ConvertFile convertFile;

    @Override
    public ResponseEntity<?> saveInventory(MultipartFile requestFile) {
        if (!TYPE.equals(requestFile.getContentType()))
            return new ResponseEntity(
                    DefaultResponse.builder().message(FILE_FORMAT).build(),
                    HttpStatus.BAD_REQUEST);
        try {
            List<InventoryEntity> inventoryEntities = convertFile.csvToInventoryEntity(requestFile.getInputStream());
            inventoryRepository.saveAll(inventoryEntities);
            return ResponseEntity.status(HttpStatus.OK).body(DefaultResponse.builder()
                    .message(SUCCESS).response(FILE_UPLOAD_SUCCESS + requestFile.getOriginalFilename()).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    DefaultResponse.builder().response(e.getMessage()).message(UN_KNOWN_ERROR).build());
        }
    }

    @Override
    public ResponseEntity<?> getFilteredData(RequestFilterRequest requestFilterRequest) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Pageable pageable = PageRequest.of(requestFilterRequest.getPageNo() - 1, requestFilterRequest.getPageSize());
            List<InventoryEntity> responseList = new ArrayList<>();
            if (requestFilterRequest.isNotExpired()) {
                responseList = inventoryRepository.findByProductNameNotExpired(requestFilterRequest.getProductName(), dtf.format(now), pageable);
            } else
                responseList = inventoryRepository.findByProductName(requestFilterRequest.getProductName(), pageable);
            if (responseList.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        DefaultResponse.builder().message(UN_SUCCESS).response(DATA_NOT_FOUND).build());
            return ResponseEntity.status(HttpStatus.OK).body(DefaultResponse.builder().message(SUCCESS).response(responseList).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    DefaultResponse.builder().response(e.getMessage()).message(UN_KNOWN_ERROR).build());
        }
    }

    @Override
    public ResponseEntity<?> filterProduct(ProductsSearchRequest request) {
        try {
            Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize());
            List<InventoryEntity> list = inventoryRepository.findByBatchIdAndStock(request.getBatch(), pageable);
            if (list.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        DefaultResponse.builder().message(UN_SUCCESS).response(DATA_NOT_FOUND).build());
            return ResponseEntity.status(HttpStatus.OK).body(DefaultResponse.builder().message(SUCCESS).response(list).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    DefaultResponse.builder().response(e.getMessage()).message(UN_KNOWN_ERROR).build());
        }
    }

}
