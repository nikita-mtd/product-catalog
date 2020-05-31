package ru.nomia.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("section")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private String parentSection;
    private List<String> sections; /* ids */
    private SectionStatus status;

    public enum SectionStatus {
        ACTIVE,
        INACTIVE
    }
}
