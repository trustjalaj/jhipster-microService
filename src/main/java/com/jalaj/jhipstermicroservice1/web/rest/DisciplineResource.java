package com.jalaj.jhipstermicroservice1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jalaj.jhipstermicroservice1.service.DisciplineService;
import com.jalaj.jhipstermicroservice1.service.dto.DisciplineDTO;
import com.jalaj.jhipstermicroservice1.web.rest.errors.BadRequestAlertException;
import com.jalaj.jhipstermicroservice1.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Discipline.
 */
@RestController
@RequestMapping("/api")
public class DisciplineResource {

    private static final String ENTITY_NAME = "discipline";
    private final Logger log = LoggerFactory.getLogger(DisciplineResource.class);
    private final DisciplineService disciplineService;

    public DisciplineResource(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    /**
     * POST  /disciplines : Create a new discipline.
     *
     * @param disciplineDTO the disciplineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disciplineDTO, or with status 400 (Bad Request) if the discipline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disciplines")
    @Timed
    public ResponseEntity<DisciplineDTO> createDiscipline(@Valid @RequestBody DisciplineDTO disciplineDTO) throws URISyntaxException {
        log.debug("REST request to save Discipline : {}", disciplineDTO);
        if (disciplineDTO.getId() != null) {
            throw new BadRequestAlertException("A new discipline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplineDTO result = disciplineService.save(disciplineDTO);
        return ResponseEntity.created(new URI("/api/disciplines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disciplines : Updates an existing discipline.
     *
     * @param disciplineDTO the disciplineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disciplineDTO,
     * or with status 400 (Bad Request) if the disciplineDTO is not valid,
     * or with status 500 (Internal Server Error) if the disciplineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disciplines")
    @Timed
    public ResponseEntity<DisciplineDTO> updateDiscipline(@Valid @RequestBody DisciplineDTO disciplineDTO) throws URISyntaxException {
        log.debug("REST request to update Discipline : {}", disciplineDTO);
        if (disciplineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisciplineDTO result = disciplineService.save(disciplineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disciplineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disciplines : get all the disciplines.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of disciplines in body
     */
    @GetMapping("/disciplines")
    @Timed
    public List<DisciplineDTO> getAllDisciplines(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Disciplines");
        return disciplineService.findAll();
    }

    /**
     * GET  /disciplines/:id : get the "id" discipline.
     *
     * @param id the id of the disciplineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disciplineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/disciplines/{id}")
    @Timed
    public ResponseEntity<DisciplineDTO> getDiscipline(@PathVariable Long id) {
        log.debug("REST request to get Discipline : {}", id);
        Optional<DisciplineDTO> disciplineDTO = disciplineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplineDTO);
    }

    /**
     * DELETE  /disciplines/:id : delete the "id" discipline.
     *
     * @param id the id of the disciplineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disciplines/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        log.debug("REST request to delete Discipline : {}", id);
        disciplineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
