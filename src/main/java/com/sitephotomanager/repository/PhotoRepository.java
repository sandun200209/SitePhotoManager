package com.sitephotomanager.repository;

import com.sitephotomanager.entity.Photo;
import com.sitephotomanager.entity.PhotoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByRoomIdOrderByUploadedAtDesc(Long roomId);

    List<Photo> findByRoomId(Long roomId);

    List<Photo> findByRoomIdAndCategoryOrderByUploadedAtDesc(Long roomId, PhotoCategory category);

    List<Photo> findTop10ByOrderByUploadedAtDesc();

    long countByRoomId(Long roomId);

    long countByRoomFloorPlanProjectId(Long projectId);
}
