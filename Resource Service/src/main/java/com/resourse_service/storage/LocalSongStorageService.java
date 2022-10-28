package com.resourse_service.storage;

import com.resourse_service.db.services.SongService;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class LocalSongStorageService implements SongStorageService {

    @Autowired
    SongService songService;


    @Override
    public String storeSong(MultipartFile file, Integer id) {
        Path destinationFile = Paths.get("/songDir");
        String path = "newAudio" + id + ".mp3";

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


    public byte[] unstoreSong(Integer id) {
        Path destinationFile = Paths.get("/songDir");
        Path filePath;
        try {
           filePath = destinationFile.resolve(songService.getById(id).getSongPath());
        } catch (LazyInitializationException e) {
            return new byte[2];
        }
        byte[] fileContent;
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
