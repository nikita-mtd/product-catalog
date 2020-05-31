package ru.nomia.web.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nomia.entity.SectionEntity;
import ru.nomia.service.SectionService;
import ru.nomia.service.SectionService.CreateSection;
import ru.nomia.service.SectionService.UpdateSection;
import ru.nomia.web.v1.dto.CreateSectionRequest;
import ru.nomia.web.v1.dto.SectionInfoMode;
import ru.nomia.web.v1.dto.SectionInfoReply;
import ru.nomia.web.v1.dto.SectionInfoReply.SectionInfoStatusReply;
import ru.nomia.web.v1.dto.UpdateSectionRequest;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;
import static ru.nomia.web.v1.dto.SectionInfoMode.FIRST_LEVEL;

@Slf4j
@RestController
@RequestMapping("sections")
@RequiredArgsConstructor
public class SectionController {

    private static final SectionInfoMode DEFAULT_SECTION_INFO_MODE = FIRST_LEVEL;

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<SectionInfoReply> createSection(@RequestBody CreateSectionRequest body) {
        var createdSection = sectionService.createSection(
                CreateSection.builder()
                        .name(body.getName())
                        .description(body.getDescription())
                        .parentSection(body.getParentSection())
                        .sections(body.getSections())
                        .build()
        );
        return ok(mapToDto(createdSection));
    }

    @GetMapping("/{id}")
    public Optional<SectionInfoReply> getSection(@PathVariable String id) {
        return sectionService
                .findActiveSection(id)
                .map(this::mapToDto);
    }
/*
    @GetMapping("/{id}")
    public Optional<SectionInfoReply> getSection(@PathVariable String id, @RequestParam String info) {
        var infoMode = getInfoModeOfDefault(info);
        switch (infoMode) {
            case FULL_TREE:
                //todo
                return ;
            case FIRST_LEVEL:
            default:
                //todo
                return ;
        }
    }

    private SectionInfoMode getInfoModeOfDefault(String modeValue) {
        try {
            return SectionInfoMode.valueOf(modeValue);
        } catch (IllegalArgumentException ex) {
            log.error("Failed sectionInfoMode convert", ex);
            return DEFAULT_SECTION_INFO_MODE;
        }
    }
*/

    @PutMapping("/{id}")
    public ResponseEntity<SectionInfoReply> updateSection(
            @PathVariable String id,
            @RequestBody UpdateSectionRequest body
    ) {
        var updatedSection = sectionService.updateSection(
                UpdateSection.builder()
                        .id(id)
                        .name(body.getName())
                        .description(body.getDescription())
                        .parentSection(body.getParentSection())
                        .sections(body.getSections())
                        .build()
        );
        return ok(mapToDto(updatedSection));
    }

    @PostMapping("/{id}/disable")
    public void disableSection(@PathVariable String id) {
        sectionService.disableSection(id);
    }

    private SectionInfoReply mapToDto(SectionEntity entity) {
        return SectionInfoReply.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .parentSection(entity.getParentSection())
                .status(SectionInfoStatusReply.valueOf(entity.getStatus().toString()))
                .build();
    }

}
