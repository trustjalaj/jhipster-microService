package com.jalaj.jhipstermicroservice1.service.mapper;

import com.jalaj.jhipstermicroservice1.domain.Course;
import com.jalaj.jhipstermicroservice1.service.dto.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", uses = {LessonMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {


    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "programs", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
