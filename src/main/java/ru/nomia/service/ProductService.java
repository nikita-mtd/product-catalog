package ru.nomia.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nomia.entity.ProductEntity;
import ru.nomia.exception.EntityNotFoundException;
import ru.nomia.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static ru.nomia.entity.ProductEntity.ProductStatus.ACTIVE;
import static ru.nomia.entity.ProductEntity.ProductStatus.INACTIVE;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductEntity createProduct(CreateProduct createProduct) {
        var newProduct = ProductEntity.builder()
                .sectionId(createProduct.getSectionId())
                .name(createProduct.getName())
                .price(createProduct.getPrice())
                .description(createProduct.getDescription())
                .status(ACTIVE)
                .build();
        return productRepository.save(newProduct);
    }

    public ProductEntity getActiveProduct(String id) {
        return findActiveProduct(id).orElseThrow(() -> new EntityNotFoundException(ProductEntity.class, id));
    }

    public List<ProductEntity> getAllActiveSectionProducts(String sectionId) {
        return productRepository.findAllBySectionIdAndStatus(sectionId, ACTIVE);
    }

    public Optional<ProductEntity> findActiveProduct(String id) {
        return productRepository.findByIdAndStatus(id, ACTIVE);
    }

    @Transactional
    public ProductEntity updateProduct(UpdateProduct updateProduct) {
        var existProduct = getActiveProduct(updateProduct.getId());
        existProduct
                .setSectionId(updateProduct.getSectionId())
                .setName(updateProduct.getName())
                .setPrice(updateProduct.getPrice())
                .setDescription(updateProduct.getDescription());
        return productRepository.save(existProduct);
    }

    @Transactional
    public ProductEntity disableProduct(String id) {
        var existProduct = getActiveProduct(id);
        existProduct.setStatus(INACTIVE);
        return productRepository.save(existProduct);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateProduct {
        private String id;
        private String sectionId;
        private String name;
        private BigDecimal price;
        private String description;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateProduct {
        private String sectionId;
        private String name;
        private BigDecimal price;
        private String description;
    }
}
