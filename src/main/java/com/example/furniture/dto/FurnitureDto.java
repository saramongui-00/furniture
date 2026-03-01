package com.example.furniture.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class FurnitureDto {
    private String name;
    private double weight;
    private double price;
    private String ip;
    private String server_message;
}
