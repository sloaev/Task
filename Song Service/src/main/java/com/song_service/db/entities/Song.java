package com.song_service.db.entities;

import com.song_service.dto.SongDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Song {
    private Integer id;
    private String artist;
    private String album;
    private String name;
    private String length;
    private Integer resourceId;
    private Integer year;

    public Song() {
    }

    public Song(SongDto dto) {
        this.artist = dto.getArtist();
        this.album = dto.getAlbum();
        this.length = dto.getLength();
        this.resourceId = dto.getResourceId();
        this.name = dto.getName();
        this.year = dto.getYear();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
