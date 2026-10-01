package com.sitephotomanager.repository;

import com.sitephotomanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    List<Project> findAllByOrderByCreatedAtDesc();

    List<Project> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String name, String code);
}
