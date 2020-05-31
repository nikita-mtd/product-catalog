package ru.nomia.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("product")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private String id;
    @Indexed
    private String sectionId;
    private String name;
    private BigDecimal price;
    private String picture;
    private String description;
    private ProductStatus status;

    public enum ProductStatus {
        ACTIVE,
        INACTIVE
    }
}
