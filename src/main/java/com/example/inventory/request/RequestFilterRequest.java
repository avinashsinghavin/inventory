package com.example.inventory.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFilterRequest {

    @NotBlank(message = "product_name is required!")
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("not_expired")
    private boolean notExpired;
    @NotNull
    @Min(value = 1, message = "page_no must be greater than 0")
    @JsonProperty("page_no")
    private Integer pageNo;
    @NotNull
    @Min(value = 1, message = "page_size must be greater than 0")
    @JsonProperty("page_size")
    private Integer pageSize;
}
