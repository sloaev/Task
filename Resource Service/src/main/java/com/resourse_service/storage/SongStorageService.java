package com.resourse_service.storage;

public interface SongStorageService {

    String storeSong(String songBody, Integer id);
    String unstoreSong(String path);
    void deleteSong(String path);
}
