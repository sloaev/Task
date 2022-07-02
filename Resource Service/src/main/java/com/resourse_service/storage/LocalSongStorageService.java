package com.resourse_service.storage;

import com.resourse_service.db.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class LocalSongStorageService implements SongStorageService  {

    @Autowired
    SongService songService;

    public String storeSong(String songBody, Integer id) {

        String base64Image = songBody.split(",")[1];
        String path = "song"+id+".mp3";

        byte[] decodedImg = Base64.getDecoder()
                .decode(base64Image.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = Paths.get("/songDir");

        if (!Files.exists(destinationFile)) {
            try {
                Files.createDirectories(destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (InputStream inputStream = new ByteArrayInputStream(decodedImg)) {
            Path filePath = destinationFile.resolve(path);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
           ioe.printStackTrace();
        }
        return path;
    }

    @Override
    public String storeSong(MultipartFile file, Integer id) {
        Path destinationFile = Paths.get("/songDir");
        String path = "newAudio" + id +".mp3";

        if (!Files.exists(destinationFile)) {
            try {
                Files.createDirectories(destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = destinationFile.resolve(path);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return path;
    }

    public String unstoreSong(String path) {
        String result = "";
        Path destinationFile = Paths.get("/songDir");
        Path filePath = destinationFile.resolve(path);
        try {
            byte[] fileContent = Files.readAllBytes(filePath);
            result = "data:audio/mpeg;base64," + Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] unstoreSong(Integer id) {
        Path destinationFile = Paths.get("/songDir");
        Path filePath = destinationFile.resolve(songService.getById(id).getSongPath());
        byte[] fileContent = new byte[0];
        try {
            fileContent = Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }

    public void deleteSong(String path) {
        Path filePath = Paths.get("/songDir").resolve(path);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
