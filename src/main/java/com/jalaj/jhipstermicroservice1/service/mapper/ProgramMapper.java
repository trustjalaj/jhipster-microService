package com.jalaj.jhipstermicroservice1.service.mapper;

import com.jalaj.jhipstermicroservice1.domain.Program;
import com.jalaj.jhipstermicroservice1.service.dto.ProgramDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Program and its DTO ProgramDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface ProgramMapper extends EntityMapper<ProgramDTO, Program> {


    @Mapping(target = "resources", ignore = true)
    @Mapping(target = "disciplines", ignore = true)
    Program toEntity(ProgramDTO programDTO);

    default Program fromId(Long id) {
        if (id == null) {
            return null;
        }
        Program program = new Program();
        program.setId(id);
        return program;
    }
}
