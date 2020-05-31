package ru.nomia.web.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nomia.entity.ProductEntity;
import ru.nomia.service.ProductService;
import ru.nomia.service.ProductService.CreateProduct;
import ru.nomia.service.ProductService.UpdateProduct;
import ru.nomia.web.v1.dto.CreateProductRequest;
import ru.nomia.web.v1.dto.ProductInfoReply;
import ru.nomia.web.v1.dto.ProductInfoReply.ProductInfoStatusReply;
import ru.nomia.web.v1.dto.UpdateProductRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductInfoReply> createProduct(@RequestBody CreateProductRequest body) {
        var createdProduct = productService.createProduct(
                CreateProduct.builder()
                        .sectionId(body.getSectionId())
                        .name(body.getName())
                        .price(body.getPrice())
                        .description(body.getDescription())
                        .build()
        );
        return ok(mapToDto(createdProduct));
    }

    @GetMapping("/{id}")
    public Optional<ProductInfoReply> getProduct(@PathVariable String id) {
        return productService
                .findActiveProduct(id)
                .map(this::mapToDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductInfoReply>> getProductsForSection(@RequestParam String sectionId) {
        var products = productService
                .getAllActiveSectionProducts(sectionId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ok(products);
    }

/*
    @GetMapping
    public ResponseEntity<List<ProductInfoReply>> getProductsForSection(
            @RequestParam String sectionId,
            @RequestParam String info
    ) {
        //todo
        var infoMode = ProductInfoMode.SHORT;
        switch (infoMode) {
            case FULL:
            case SHORT:

            case ID:
            default:
        }
    }
    */

    @PutMapping("/{id}")
    public ResponseEntity<ProductInfoReply> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest body) {
        var updatedProduct = productService.updateProduct(
                UpdateProduct.builder()
                        .id(id)
                        .sectionId(body.getSectionId())
                        .name(body.getName())
                        .price(body.getPrice())
                        .description(body.getDescription())
                        .build()
        );
        return ok(mapToDto(updatedProduct));
    }

    //endpoint for section with products??

    @PostMapping("/{id}/disable")
    public void disableProduct(@PathVariable String id) {
        productService.disableProduct(id);
    }

    private ProductInfoReply mapToDto(ProductEntity entity) {
        return ProductInfoReply.builder()
                .id(entity.getId())
                .sectionId(entity.getSectionId())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .status(ProductInfoStatusReply.valueOf(entity.getStatus().toString()))
                .build();
    }

}
