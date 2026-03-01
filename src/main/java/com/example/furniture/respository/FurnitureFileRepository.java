package com.example.furniture.respository;

import com.example.furniture.model.Furniture;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Repository
public class FurnitureFileRepository {
    @Value("${furniture.file.path}")
    private String filePath;
    private Path path;


    @PostConstruct
    public void init() {
        try {
            path = Paths.get(filePath);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing file", e);
        }
    }

    public void save(Furniture furniture) {
        try {
            String encodedName = Base64.getEncoder()
                    .encodeToString(furniture.getName().getBytes());

            String line = furniture.getId() + ";" +
                    encodedName + ";" +
                    furniture.getWeight() + ";" +
                    furniture.getPrice() + System.lineSeparator();

            Files.write(path,
                    line.getBytes(),
                    StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Error saving furniture", e);
        }
    }

    public List<Furniture> findAll() {
        List<Furniture> furnitures = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {

                if (line.isBlank()) continue;

                String[] parts = line.split(";");

                Long id = Long.parseLong(parts[0]);

                String name = new String(
                        Base64.getDecoder().decode(parts[1])
                );

                double weight = Double.parseDouble(parts[2]);

                double price = Double.parseDouble(parts[3]);

                Furniture furniture = new Furniture(id, name, weight, price);
                furnitures.add(furniture);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }

        return furnitures;
    }

}
