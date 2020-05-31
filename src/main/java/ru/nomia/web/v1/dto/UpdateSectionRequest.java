package ru.nomia.web.v1.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateSectionRequest {
    private String name;
    private String description;
    private String parentSection;
    private List<String> sections;
}
