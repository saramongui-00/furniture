package com.example.furniture.service;

import com.example.furniture.dto.*;
import com.example.furniture.model.entity.FurnitureEntity;
import com.example.furniture.repository.FurnitureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FurnitureService {

    private static final Logger log = LoggerFactory.getLogger(FurnitureService.class);

    private final FurnitureRepository repository;

    @Value("${server.message}")
    private String serverMessage;

    public FurnitureService(FurnitureRepository repository) {
        this.repository = repository;
    }

    public ResponseWrapped<FurnitureResponse> save(FurnitureRequest request) {
        log.info("Guardando mueble: {}", request);

        FurnitureEntity entity = new FurnitureEntity(
                null,
                request.getName(),
                request.getWeight(),
                request.getPrice());

        FurnitureEntity saved = repository.save(entity);
        log.info("Mueble guardado con ID={}", saved.getId());

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
        log.debug("Obteniendo lista de muebles");
        List<FurnitureDto> list = repository.findAll()
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
        log.info("Se obtuvieron {} muebles", list.size());
        return list;
    }

    private String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }
}