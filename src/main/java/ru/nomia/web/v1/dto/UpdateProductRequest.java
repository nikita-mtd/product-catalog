package ru.nomia.web.v1.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String sectionId;
    private String name;
    private BigDecimal price;
    private String description;
}
