package ru.nomia.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nomia.entity.SectionEntity;
import ru.nomia.exception.EntityNotFoundException;
import ru.nomia.exception.SectionLoopException;
import ru.nomia.repository.SectionRepository;

import java.util.*;

import static ru.nomia.entity.SectionEntity.SectionStatus.ACTIVE;
import static ru.nomia.entity.SectionEntity.SectionStatus.INACTIVE;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionEntity createSection(CreateSection createSection) {
        var newSection = SectionEntity.builder()
                .name(createSection.getName())
                .description(createSection.getDescription())
                .parentSection(createSection.getParentSection())
                .sections(createSection.getSections())
                .status(ACTIVE)
                .build();
        checkSectionLoop(newSection);
        return sectionRepository.save(newSection);
    }

    public SectionEntity getSection(String id) {
        return findActiveSection(id).orElseThrow(() -> new EntityNotFoundException(SectionEntity.class, id));
    }

    public Optional<SectionEntity> findActiveSection(String id) {
        return sectionRepository.findByIdAndStatus(id, ACTIVE);
    }

    @Transactional
    public SectionEntity updateSection(UpdateSection updateSection) {
        var existSection = getSection(updateSection.getId());
        existSection
                .setName(updateSection.getName())
                .setDescription(updateSection.getDescription())
                .setParentSection(updateSection.getParentSection())
                .setSections(updateSection.getSections());
        checkSectionLoop(existSection);
        return sectionRepository.save(existSection);
    }

    @Transactional
    public SectionEntity disableSection(String id) {
        var existSection = getSection(id);
        existSection.setStatus(INACTIVE);
        return sectionRepository.save(existSection);
    }

    private void checkSectionLoop(SectionEntity section) {
        if (isSectionLoop(section.getId(), section.getSections())) {
            throw new SectionLoopException();
        }
    }

    private boolean isSectionLoop(String id, List<String> start) {
        //todo: inform log about lvl fail of sections b-tree
        //todo: timeout for checking
        Queue<String> queue = new LinkedList<>(start);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (id.equals(current)) return true;
            var entity = findActiveSection(current);
            if (entity.isPresent() && entity.get().getSections() != null) {
                queue.addAll(entity.get().getSections());
            }
        }
        return false;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSection {
        private String name;
        private String description;
        private String parentSection;
        private List<String> sections;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateSection {
        private String id;
        private String name;
        private String description;
        private String parentSection;
        private List<String> sections;
    }
}
