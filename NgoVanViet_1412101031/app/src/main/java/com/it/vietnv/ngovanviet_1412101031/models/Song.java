package com.it.vietnv.ngovanviet_1412101031.models;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String songName;
    private String singerName;
    private String avatar;
    private String link;

    public Song() {
    }

    public Song(int id, String songName, String singerName, String avatar, String link) {
        this.id = id;
        this.songName = songName;
        this.singerName = singerName;
        this.avatar = avatar;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
