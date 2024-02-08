package com.rolliedev.service;

import com.rolliedev.util.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {

    private static final ImageService INSTANCE = new ImageService();
    private static final String BASE_PATH = PropertiesUtil.get("image.base.url");

    @SneakyThrows
    public Optional<InputStream> get(String imagePath) {
        var imageFullPath = Path.of(BASE_PATH, imagePath);
        return Files.exists(imageFullPath)
                ? Optional.of(Files.newInputStream(imageFullPath))
                : Optional.empty();

    }

    public static ImageService getInstance() {
        return INSTANCE;
    }
}
