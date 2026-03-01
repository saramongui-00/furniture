package com.example.furniture.dto;

import lombok.Data;

@Data
public class FurnitureResponse {

    private Long id;
    private String name;
    private double weight;
    private double price;
    private String message;
    private String server_message;
}
