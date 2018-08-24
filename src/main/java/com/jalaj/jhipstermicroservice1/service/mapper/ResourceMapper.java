package com.jalaj.jhipstermicroservice1.service.mapper;

import com.jalaj.jhipstermicroservice1.domain.Resource;
import com.jalaj.jhipstermicroservice1.service.dto.ResourceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Resource and its DTO ResourceDTO.
 */
@Mapper(componentModel = "spring", uses = {DisciplineMapper.class, ProgramMapper.class, CourseMapper.class, LessonMapper.class})
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {

    @Mapping(source = "discipline.id", target = "disciplineId")
    @Mapping(source = "program.id", target = "programId")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "lesson.id", target = "lessonId")
    ResourceDTO toDto(Resource resource);

    @Mapping(source = "disciplineId", target = "discipline")
    @Mapping(source = "programId", target = "program")
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "lessonId", target = "lesson")
    Resource toEntity(ResourceDTO resourceDTO);

    default Resource fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resource resource = new Resource();
        resource.setId(id);
        return resource;
    }
}
