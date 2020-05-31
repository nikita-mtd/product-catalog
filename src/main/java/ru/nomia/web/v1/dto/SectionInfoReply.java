package ru.nomia.web.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionInfoReply {

    private String id;
    private String name;
    private String description;
    private String parentSection;
    private List<String> sections;
    private SectionInfoStatusReply status;

    public enum SectionInfoStatusReply {
        ACTIVE,
        INACTIVE
    }

}
