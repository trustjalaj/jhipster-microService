package com.jalaj.jhipstermicroservice1.repository;

import com.jalaj.jhipstermicroservice1.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Resource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
