package com.song_service.dto;

import com.song_service.db.entities.Song;

public class SongDto {
    private String artist;
    private String album;
    private String name;
    private String length;
    private Integer resourceId;
    private Integer year;

    public SongDto(){
    }

    public SongDto(String artist, String album, String name, String length, Integer resourceId, Integer year) {
        this.artist = artist;
        this.album = album;
        this.name = name;
        this.length = length;
        this.resourceId = resourceId;
        this.year = year;
    }

  public SongDto(Song song) {
        this.artist = song.getArtist();
        this.album = song.getAlbum();
        this.name = song.getName();
        this.length = song.getLength();
        this.resourceId = song.getResourceId();
        this.year = song.getYear();
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
