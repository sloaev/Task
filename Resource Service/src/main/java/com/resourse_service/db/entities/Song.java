package com.resourse_service.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Song")
public class Song {
    private Integer id;

    private String songPath;

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songBody) {
        this.songPath = songBody;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }
}
