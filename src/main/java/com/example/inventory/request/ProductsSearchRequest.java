package com.example.inventory.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsSearchRequest {
    @NotNull(message = "batch is required!")
    private String batch;
    @NotNull
    @Min(1)
    @JsonProperty("page_no")
    private Integer pageNo;
    @NotNull
    @Min(1)
    @JsonProperty("page_size")
    private Integer pageSize;
}
