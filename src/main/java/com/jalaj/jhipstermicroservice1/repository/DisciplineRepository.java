package com.jalaj.jhipstermicroservice1.repository;

import com.jalaj.jhipstermicroservice1.domain.Discipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Discipline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    @Query(value = "select distinct discipline from Discipline discipline left join fetch discipline.programs",
        countQuery = "select count(distinct discipline) from Discipline discipline")
    Page<Discipline> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct discipline from Discipline discipline left join fetch discipline.programs")
    List<Discipline> findAllWithEagerRelationships();

    @Query("select discipline from Discipline discipline left join fetch discipline.programs where discipline.id =:id")
    Optional<Discipline> findOneWithEagerRelationships(@Param("id") Long id);

}
