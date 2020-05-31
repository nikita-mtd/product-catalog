package ru.nomia.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.nomia.entity.SectionEntity;
import ru.nomia.entity.SectionEntity.SectionStatus;

import java.util.Optional;

@Repository
public interface SectionRepository extends MongoRepository<SectionEntity, String> {

    Optional<SectionEntity> findByIdAndStatus(String id, SectionStatus status);
}
