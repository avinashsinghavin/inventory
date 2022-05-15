package com.example.inventory.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory_details")
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String batch;
    private Long stock;
    private Integer deal;
    private Integer free;
    private Double mrp;
    private Double rate;
    @Column(name = "expire_date")
    private String exp;
    private String company;
    private String supplier;
}
