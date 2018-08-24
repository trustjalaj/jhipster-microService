package com.jalaj.jhipstermicroservice1.service.impl;

import com.jalaj.jhipstermicroservice1.domain.Discipline;
import com.jalaj.jhipstermicroservice1.repository.DisciplineRepository;
import com.jalaj.jhipstermicroservice1.service.DisciplineService;
import com.jalaj.jhipstermicroservice1.service.dto.DisciplineDTO;
import com.jalaj.jhipstermicroservice1.service.mapper.DisciplineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Discipline.
 */
@Service
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    private final Logger log = LoggerFactory.getLogger(DisciplineServiceImpl.class);

    private final DisciplineRepository disciplineRepository;

    private final DisciplineMapper disciplineMapper;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
    }

    /**
     * Save a discipline.
     *
     * @param disciplineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DisciplineDTO save(DisciplineDTO disciplineDTO) {
        log.debug("Request to save Discipline : {}", disciplineDTO);
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    /**
     * Get all the disciplines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DisciplineDTO> findAll() {
        log.debug("Request to get all Disciplines");
        return disciplineRepository.findAllWithEagerRelationships().stream()
            .map(disciplineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Discipline with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DisciplineDTO> findAllWithEagerRelationships(Pageable pageable) {
        return disciplineRepository.findAllWithEagerRelationships(pageable).map(disciplineMapper::toDto);
    }


    /**
     * Get one discipline by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DisciplineDTO> findOne(Long id) {
        log.debug("Request to get Discipline : {}", id);
        return disciplineRepository.findOneWithEagerRelationships(id)
            .map(disciplineMapper::toDto);
    }

    /**
     * Delete the discipline by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discipline : {}", id);
        disciplineRepository.deleteById(id);
    }
}
