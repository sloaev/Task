package com.resourse_service.storage;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class LocalSongStorageServiceTest {

    @Autowired
    LocalSongStorageService localSongStorageService;

    private static final MockMultipartFile file = new MockMultipartFile("data",
            "testMusic.mp3",
            "audio/mpeg",
            "some song".getBytes());

    @Test
    @Order(0)
    void storeSong() {
        assertTrue(localSongStorageService.storeSong(file,1).contains("newAudio1.mp3"));
    }

    @Test
    @Order(1)
    void deleteSong() {
        localSongStorageService.deleteSong("newAudio1.mp3");
    }
}