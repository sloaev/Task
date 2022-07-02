package com.resourse_service.controllers;

import com.resourse_service.db.entities.Song;
import com.resourse_service.db.services.SongService;
import com.resourse_service.storage.SongStorageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    public String hey(){
        return "Hello there";
    }

    @PostMapping(value = "/upload", consumes = {"audio/mpeg"},produces = {"application/json"})
    public ResponseEntity<String> uploadSong(@RequestBody String songBody){
        Song song = songService.create();
        song.setSongPath(songStorageService.storeSong(songBody,song.getId()));
        songService.save(song);
        rabbitTemplate.convertAndSend("REStoREP",song.getId());
        return ResponseEntity.ok("{\"id\":\"" +song.getId()+ "\"}");
    }

    @PostMapping(value = "/uploadv2",produces = {"application/json"})
    public ResponseEntity<String> uploadSongv2(@RequestBody MultipartFile file){
        Song song = songService.create();
        song.setSongPath(songStorageService.storeSong(file,song.getId()));
        songService.save(song);
        rabbitTemplate.convertAndSend("REStoREP",song.getId());
        return ResponseEntity.ok("{\"id\":\"" +song.getId() + "\"}");
    }

    @GetMapping(value = "/download")
    public String downloadSong(@RequestParam(value = "id") Integer id) {
        return songStorageService.unstoreSong(songService.getById(id).getSongPath());
    }

    @GetMapping(value = "/downloadv2")
    public byte[] downloadSongV2(@RequestParam(value = "id") Integer id) {
        return songStorageService.unstoreSong(id);
    }

    @DeleteMapping(value = "delete", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<List<Integer>> delete(@RequestBody List<Integer> ids){
        for (Integer id: ids) {
            Song song = songService.getById(id);
            songStorageService.deleteSong(song.getSongPath());
            songService.delete(song);
        }
        return ResponseEntity.ok(ids);
    }

}
