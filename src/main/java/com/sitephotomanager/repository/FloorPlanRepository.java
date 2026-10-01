package com.sitephotomanager.repository;

import com.sitephotomanager.entity.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {

    List<FloorPlan> findByProjectId(Long projectId);

    List<FloorPlan> findByProjectIdOrderByCreatedAtDesc(Long projectId);

    long countByProjectId(Long projectId);
}
