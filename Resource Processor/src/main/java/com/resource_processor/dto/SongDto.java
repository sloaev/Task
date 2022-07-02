package com.resource_processor.dto;

import org.apache.tika.metadata.Metadata;

public class SongDto {
    private String artist;
    private String album;
    private String name;
    private String length;
    private Integer resourceId;
    private String year;

    public SongDto(){
    }
    public SongDto(Metadata metadata){
        this.artist = metadata.get("xmpDM:artist");
        this.album = metadata.get("xmpDM:album");
        this.name = metadata.get("dc:title");
        this.year = metadata.get("xmpDM:year");
        this.length = metadata.get("xmpDM:duration");
    }

    public SongDto(String artist, String album, String name, String length, Integer resourceId, String year) {
        this.artist = artist;
        this.album = album;
        this.name = name;
        this.length = length;
        this.resourceId = resourceId;
        this.year = year;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
