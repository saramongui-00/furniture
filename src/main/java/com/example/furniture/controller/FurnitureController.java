package com.example.furniture.controller;

import com.example.furniture.dto.*;
import com.example.furniture.service.FurnitureService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/furniture")
public class FurnitureController {

    private static final Logger log = LoggerFactory.getLogger(FurnitureController.class);
    private final FurnitureService furnitureService;

    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @PostMapping
    public ResponseWrapped<FurnitureResponse> createFurniture(@RequestBody FurnitureRequest request) {
        log.info("Solicitud recibida para crear mueble: {}", request);
        ResponseWrapped<FurnitureResponse> response = furnitureService.save(request);
        log.info("Mueble creado con ID={} y nombre={}",
                response.getData().getId(), response.getData().getName());
        return response;
    }

    @GetMapping
    public List<FurnitureDto> getFurniture() { // Cambié el nombre de getPeople a getFurniture
        log.info("Solicitud recibida para listar todos los muebles");
        List<FurnitureDto> muebles = furnitureService.getAll();
        log.info("Se retornaron {} muebles", muebles.size());
        return muebles;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureDto> getFurnitureById(@PathVariable Long id) {
        log.info("Solicitud recibida para obtener mueble con ID: {}", id);

        try {
            FurnitureDto furniture = furnitureService.getById(id);
            log.info("Mueble encontrado: ID={}, nombre={}", id, furniture.getName());
            return ResponseEntity.ok(furniture);
        } catch (RuntimeException e) {
            log.error("Mueble no encontrado con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINA ESTE MÉTODO COMPLETO:
    // @GetMapping("/paged")
    // public Page<FurnitureDto> getFurniturePaged(...) { ... }
}