package com.jalaj.jhipstermicroservice1.service.mapper;

import com.jalaj.jhipstermicroservice1.domain.Discipline;
import com.jalaj.jhipstermicroservice1.service.dto.DisciplineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Discipline and its DTO DisciplineDTO.
 */
@Mapper(componentModel = "spring", uses = {ProgramMapper.class})
public interface DisciplineMapper extends EntityMapper<DisciplineDTO, Discipline> {


    @Mapping(target = "resources", ignore = true)
    Discipline toEntity(DisciplineDTO disciplineDTO);

    default Discipline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discipline discipline = new Discipline();
        discipline.setId(id);
        return discipline;
    }
}
