package com.example.furniture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Furniture {
    private Long id;
    private String name;
    private double weight;
    private double price;
}
