package ru.nomia.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.nomia.entity.ProductEntity;
import ru.nomia.entity.ProductEntity.ProductStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    Optional<ProductEntity> findByIdAndStatus(String id, ProductStatus status);

    List<ProductEntity> findAllBySectionIdAndStatus(String sectionId, ProductStatus status);
}
