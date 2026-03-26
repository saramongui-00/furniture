package com.example.furniture.repository;

import com.example.furniture.model.entity.FurnitureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnitureRepository extends JpaRepository<FurnitureEntity, Long> {
}