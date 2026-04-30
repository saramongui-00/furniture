package com.example.furniture.repository;

import com.example.furniture.model.entity.FurnitureEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FurnitureRepository extends JpaRepository<FurnitureEntity, Long> {

    List<FurnitureEntity> findAllByOrderByIdDesc(Pageable pageable);

    @Query("""
        SELECT f FROM FurnitureEntity f
        WHERE f.id < :cursor
        ORDER BY f.id DESC
    """)
    List<FurnitureEntity> findNextPage(Long cursor, Pageable pageable);
}