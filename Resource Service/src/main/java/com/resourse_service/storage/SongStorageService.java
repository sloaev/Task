package com.resourse_service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface SongStorageService {

    String storeSong(MultipartFile file, Integer id);
    byte[] unstoreSong(Integer id);
    void deleteSong(String path);
}
