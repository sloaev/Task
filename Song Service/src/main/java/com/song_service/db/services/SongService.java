package com.song_service.db.services;

import com.song_service.db.entities.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    void deleteAllByResourceIdIn(List<Integer> ids);

    default Song create(){
        Song song = new Song();
        save(song);
        return song;
    }
}
