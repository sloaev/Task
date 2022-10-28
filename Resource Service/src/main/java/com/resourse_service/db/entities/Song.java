package com.resourse_service.db.entities;


import javax.persistence.*;

@Entity(name = "Song")
public class Song {
    @Basic(fetch = FetchType.EAGER)
    private Integer id;
    @Basic(fetch = FetchType.EAGER)
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
