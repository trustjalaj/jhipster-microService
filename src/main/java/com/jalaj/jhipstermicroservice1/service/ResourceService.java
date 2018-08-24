package com.jalaj.jhipstermicroservice1.service;

import com.jalaj.jhipstermicroservice1.service.dto.ResourceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Resource.
 */
public interface ResourceService {

    /**
     * Save a resource.
     *
     * @param resourceDTO the entity to save
     * @return the persisted entity
     */
    ResourceDTO save(ResourceDTO resourceDTO);

    /**
     * Get all the resources.
     *
     * @return the list of entities
     */
    List<ResourceDTO> findAll();


    /**
     * Get the "id" resource.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResourceDTO> findOne(Long id);

    /**
     * Delete the "id" resource.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
