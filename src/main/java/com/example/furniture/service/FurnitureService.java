package com.example.furniture.service;

import com.example.furniture.dto.*;
import com.example.furniture.model.entity.FurnitureEntity;
import com.example.furniture.repository.FurnitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FurnitureService {

    private final FurnitureRepository repository;

    @Value("${server.message}")
    private String serverMessage;

    // ===============================
    // CREATE (no cambia)
    // ===============================
    public ResponseWrapped<FurnitureResponse> save(FurnitureRequest request) {
        FurnitureEntity entity = new FurnitureEntity(
                null,
                request.getName(),
                request.getWeight(),
                request.getPrice()
        );

        repository.save(entity);

        FurnitureResponse response = new FurnitureResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setWeight(entity.getWeight());
        response.setPrice(entity.getPrice());
        response.setMessage("Furniture created successfully");
        response.setServer_message(serverMessage);

        return new ResponseWrapped<>(getIp(), response);
    }

    // ===============================
    // 🔥 CURSOR PAGINATION
    // ===============================
    public List<FurnitureDto> getFurnitureCursor(Long lastId, int size) {

        Pageable pageable = PageRequest.of(0, size);
        List<FurnitureEntity> entities;

        if (lastId == null) {
            // primera página
            entities = repository.findAllByOrderByIdDesc(pageable);
        } else {
            // siguientes páginas
            entities = repository.findNextPage(lastId, pageable);
        }

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    // ===============================
    // GET BY ID (no cambia)
    // ===============================
    public FurnitureDto getById(Long id) {
        FurnitureEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        return toDto(entity);
    }

    // ===============================
    // MAPPERS
    // ===============================
    private FurnitureDto toDto(FurnitureEntity entity) {
        FurnitureDto dto = new FurnitureDto();
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setWeight(entity.getWeight());
        dto.setIp(getIp());
        dto.setServer_message(serverMessage);
        return dto;
    }

    private String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }
}