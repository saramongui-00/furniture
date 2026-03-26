package com.example.furniture.service;

import com.example.furniture.dto.*;
import com.example.furniture.model.entity.FurnitureEntity;
import com.example.furniture.repository.FurnitureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FurnitureService {

    private final FurnitureRepository repository;

    @Value("${server.message}")
    private String serverMessage;

    public FurnitureService(FurnitureRepository repository) {
        this.repository = repository;
    }

    public ResponseWrapped<FurnitureResponse> save(FurnitureRequest request) {

        FurnitureEntity entity = new FurnitureEntity(
                null,
                request.getName(),
                request.getWeight(),
                request.getPrice()
        );

        FurnitureEntity saved = repository.save(entity);

        FurnitureResponse response = new FurnitureResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setWeight(saved.getWeight());
        response.setPrice(saved.getPrice());
        response.setMessage("Furniture created!");
        response.setServer_message(serverMessage);

        return new ResponseWrapped<>(getIp(), response);
    }

    public List<FurnitureDto> getAll() {
        return repository.findAll()
                .stream()
                .map(entity -> {
                    FurnitureDto dto = new FurnitureDto();
                    dto.setName(entity.getName());
                    dto.setWeight(entity.getWeight());
                    dto.setPrice(entity.getPrice());
                    dto.setIp(getIp());
                    dto.setServer_message(serverMessage);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }
}