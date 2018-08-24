package com.jalaj.jhipstermicroservice1.service;

import com.jalaj.jhipstermicroservice1.service.dto.DisciplineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Discipline.
 */
public interface DisciplineService {

    /**
     * Save a discipline.
     *
     * @param disciplineDTO the entity to save
     * @return the persisted entity
     */
    DisciplineDTO save(DisciplineDTO disciplineDTO);

    /**
     * Get all the disciplines.
     *
     * @return the list of entities
     */
    List<DisciplineDTO> findAll();

    /**
     * Get all the Discipline with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<DisciplineDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" discipline.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DisciplineDTO> findOne(Long id);

    /**
     * Delete the "id" discipline.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
