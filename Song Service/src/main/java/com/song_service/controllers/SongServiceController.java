package com.song_service.controllers;

import com.song_service.db.entities.Song;
import com.song_service.db.services.SongService;
import com.song_service.dto.SongDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "soser")
@RestController
public class SongServiceController {

    @Autowired
    SongService songService;

    @PostMapping(value = "/postInfo", consumes = {"application/json"})
    public ResponseEntity<String> postInfo(@RequestBody SongDto dto){
        Song song = new Song(dto);
        songService.save(song);
        return ResponseEntity.ok("{\"id\":\"" +song.getId()+ "\"}");
    }

    @GetMapping(value = "/getInfo",produces = {"application/json"})
    public ResponseEntity<SongDto> getInfo(@RequestParam(name = "id") Integer id){
        Song song = songService.getById(id);
        SongDto songDto = new SongDto(song);
        return ResponseEntity.ok(songDto);
    }

    @DeleteMapping(value = "/delete", produces = {"application/json"})
    public ResponseEntity<List<Integer>> deleteInfo(@RequestBody List<Integer> ids) {
        songService.deleteAllByResourceIdIn(ids);
        return ResponseEntity.ok(ids);
    }
}
