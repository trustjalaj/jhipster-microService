package com.jalaj.jhipstermicroservice1.service.impl;

import com.jalaj.jhipstermicroservice1.domain.Program;
import com.jalaj.jhipstermicroservice1.repository.ProgramRepository;
import com.jalaj.jhipstermicroservice1.service.ProgramService;
import com.jalaj.jhipstermicroservice1.service.dto.ProgramDTO;
import com.jalaj.jhipstermicroservice1.service.mapper.ProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Program.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
    }

    /**
     * Save a program.
     *
     * @param programDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgramDTO save(ProgramDTO programDTO) {
        log.debug("Request to save Program : {}", programDTO);
        Program program = programMapper.toEntity(programDTO);
        program = programRepository.save(program);
        return programMapper.toDto(program);
    }

    /**
     * Get all the programs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProgramDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable)
            .map(programMapper::toDto);
    }

    /**
     * Get all the Program with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProgramDTO> findAllWithEagerRelationships(Pageable pageable) {
        return programRepository.findAllWithEagerRelationships(pageable).map(programMapper::toDto);
    }


    /**
     * Get one program by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramDTO> findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findOneWithEagerRelationships(id)
            .map(programMapper::toDto);
    }

    /**
     * Delete the program by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.deleteById(id);
    }
}
