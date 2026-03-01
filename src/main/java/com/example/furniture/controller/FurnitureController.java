package com.example.furniture.controller;

import com.example.furniture.dto.*;
import com.example.furniture.service.FurnitureService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/furniture")
public class FurnitureController {

    private final FurnitureService furnitureService;

    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @PostMapping
    public ResponseWrapped<FurnitureResponse> createFurniture(@RequestBody FurnitureRequest request) {
        return furnitureService.save(request);
    }

    @GetMapping
    public List<FurnitureDto> getPeople(){
        return furnitureService.getAll();
    }


}
