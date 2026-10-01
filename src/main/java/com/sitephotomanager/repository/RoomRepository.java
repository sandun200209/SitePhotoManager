package com.sitephotomanager.repository;

import com.sitephotomanager.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByFloorPlanId(Long floorPlanId);

    List<Room> findByFloorPlanIdOrderByCreatedAtAsc(Long floorPlanId);

    long countByFloorPlanId(Long floorPlanId);
}
