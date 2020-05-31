package ru.nomia.web.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoReply {
    private String id;
    private String sectionId;
    private String name;
    private BigDecimal price;
    private String description;
    private ProductInfoStatusReply status;

    public enum ProductInfoStatusReply {
        ACTIVE,
        INACTIVE
    }
}
