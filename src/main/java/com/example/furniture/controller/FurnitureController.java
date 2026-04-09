package com.example.furniture.controller;

import com.example.furniture.dto.*;
import com.example.furniture.service.FurnitureService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
    public List<FurnitureDto> getPeople() {
        log.debug("Solicitud recibida para listar muebles");
        List<FurnitureDto> muebles = furnitureService.getAll();
        log.info("Se retornaron {} muebles", muebles.size());
        return muebles;
    }

    @GetMapping("/paged")
    public Page<FurnitureDto> getFurniturePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Solicitud recibida para listar muebles paginados: page={}, size={}", page, size);
        Page<FurnitureDto> muebles = furnitureService.getAllPaged(page, size);
        log.info("Se retornaron {} muebles en la página {}", muebles.getNumberOfElements(), page);
        return muebles;
    }
}
