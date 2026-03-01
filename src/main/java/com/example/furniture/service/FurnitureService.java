package com.example.furniture.service;

import com.example.furniture.dto.FurnitureDto;
import com.example.furniture.dto.FurnitureRequest;
import com.example.furniture.dto.FurnitureResponse;
import com.example.furniture.dto.ResponseWrapped;
import com.example.furniture.model.Furniture;
import com.example.furniture.respository.FurnitureFileRepository;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.List;

@Service
public class FurnitureService {

    private final FurnitureFileRepository repository;

    public FurnitureService(FurnitureFileRepository repository) {
        this.repository = repository;
    }

    public List<FurnitureDto> getAll(){

        return repository.findAll().stream().map(furniture -> {
            FurnitureDto dto = new FurnitureDto();
            dto.setName(furniture.getName());
            dto.setWeight(furniture.getWeight());
            dto.setPrice(furniture.getPrice());
            dto.setIp(ipServer());
            return dto;
        }).toList();
    }

    private String ipServer() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseWrapped<FurnitureResponse> save(FurnitureRequest request){

        Furniture newFurniture = new Furniture(1L, request.getName(), request.getWeight(), request.getPrice());

        repository.save(newFurniture);

        FurnitureResponse response = new FurnitureResponse();
        response.setId(newFurniture.getId());
        response.setName(newFurniture.getName());
        response.setWeight(newFurniture.getWeight());
        response.setPrice(newFurniture.getPrice());
        response.setMessage("Mueble Registrado !!");

        ResponseWrapped<FurnitureResponse> wrapper = new ResponseWrapped<>();
        wrapper.setIp(ipServer());
        wrapper.setData(response);
        return wrapper;
    }
}
