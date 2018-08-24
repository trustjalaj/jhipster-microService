package com.jalaj.jhipstermicroservice1.repository;

import com.jalaj.jhipstermicroservice1.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "select distinct course from Course course left join fetch course.lessons",
        countQuery = "select count(distinct course) from Course course")
    Page<Course> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct course from Course course left join fetch course.lessons")
    List<Course> findAllWithEagerRelationships();

    @Query("select course from Course course left join fetch course.lessons where course.id =:id")
    Optional<Course> findOneWithEagerRelationships(@Param("id") Long id);

}
