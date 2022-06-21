package com.resourse_service.db.services;

import com.resourse_service.db.entities.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongService extends CrudRepository<Song, Integer> {

    /**
     * @param id id of Song
     * @return Song instance with current id
     */
    Song getById(Integer id);

    /**
     * @return list of all Song instances from db
     */
    List<Song> findAllBy();

    default Song create(){
        Song song = new Song();
        save(song);
        return song;
    }

}
