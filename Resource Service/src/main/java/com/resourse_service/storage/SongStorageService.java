package com.resourse_service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface SongStorageService {

    String storeSong(String songBody, Integer id);

    String storeSong(MultipartFile file, Integer id);
    String unstoreSong(String path);
    byte[] unstoreSong(Integer id);
    void deleteSong(String path);
}
