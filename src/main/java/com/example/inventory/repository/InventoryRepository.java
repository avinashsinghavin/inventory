package com.example.inventory.repository;

import com.example.inventory.entity.InventoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    @Query("SELECT m FROM InventoryEntity m WHERE m.name LIKE %:product_name%")
    List<InventoryEntity> findByProductName(@Param("product_name") String productName, Pageable pageable);

    @Query("SELECT m FROM InventoryEntity m WHERE m.name LIKE %:product_name% AND m.exp >= :expire_date")
    List<InventoryEntity> findByProductNameNotExpired(@Param("product_name") String productName,
                                                      @Param("expire_date") String date, Pageable pageable);

    @Query(value = "SELECT m FROM InventoryEntity m WHERE m.batch = ?1 AND m.stock > 0")
    List<InventoryEntity> findByBatchIdAndStock(String batch, Pageable pageable);
}
