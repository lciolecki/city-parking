package com.city.parkingMeter.infrastructure.tools;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonTestResourceContentFileReader {

    public static String readAsString(String fileName) {
        try {
            File file = readFile(fileName);
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist", e);
        } catch (IOException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    static File readFile(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/json/" + fileName);
        return classPathResource.getFile();
    }
}
