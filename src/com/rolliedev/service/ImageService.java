package com.rolliedev.service;

import com.rolliedev.util.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {

    private static final ImageService INSTANCE = new ImageService();
    private static final String BASE_PATH = PropertiesUtil.get("image.base.url");

    @SneakyThrows
    public void upload(String imagePath, InputStream imageContent) {
        var imgFullPath = Path.of(BASE_PATH, imagePath);
        try (imageContent) {
            Files.createDirectories(imgFullPath.getParent());
            Files.write(imgFullPath, imagePath.getBytes());
        }
    }

    @SneakyThrows
    public Optional<InputStream> get(String imagePath) {
        var imgFullPath = Path.of(BASE_PATH, imagePath);
        return Files.exists(imgFullPath)
                ? Optional.of(Files.newInputStream(imgFullPath))
                : Optional.empty();
    }

    public static ImageService getInstance() {
        return INSTANCE;
    }
}
