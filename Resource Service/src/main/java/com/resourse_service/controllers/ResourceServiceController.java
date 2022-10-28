package com.resourse_service.controllers;

import com.resourse_service.db.entities.Song;
import com.resourse_service.db.services.SongService;
import com.resourse_service.storage.SongStorageService;
import org.hibernate.LazyInitializationException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "reser")
public class ResourceServiceController {

    @Autowired
    SongService songService;
    @Autowired
    SongStorageService songStorageService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/hey")
    public String hey() {
        return "Hello there";
    }

    @PostMapping(value = "/uploadv2", produces = {"application/json"})
    public ResponseEntity<String> uploadSongv2(@RequestParam(name = "file") MultipartFile file,
                                               @RequestHeader(required = false, value = "test") Boolean test) {
        Song song = songService.create();
        song.setSongPath(songStorageService.storeSong(file, song.getId()));
        songService.save(song);
        if (test == null || !test) {
            rabbitTemplate.convertAndSend("ResToRepCRE", song.getId());
        }
        return ResponseEntity.ok("{\"id\":\"" + song.getId() + "\"}");
    }

    @GetMapping(value = "/downloadv2")
    public byte[] downloadSongV2(@RequestParam(value = "id") Integer id) {
        return songStorageService.unstoreSong(id);
    }

    @DeleteMapping(value = "delete", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity delete(@RequestBody List<Integer> ids,
                                 @RequestHeader(required = false, value = "test") Boolean test) {

        try {
            for (Integer id : ids) {
                Song song = songService.getById(id);
                songStorageService.deleteSong(song.getSongPath());
                songService.delete(song);
            }
            if (test == null || !test) {
                rabbitTemplate.convertAndSend("ResToRepDEL", ids);
            }
            return ResponseEntity.ok(ids);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
