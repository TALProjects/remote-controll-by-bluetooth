package com.jrq.remoterelaynewway.FinderDevices;

/**
 * Created by jrq on 2016-09-14.
 */

public class DevicesProperties {
    private String title;
    private int thumbnail;
    private int imageLight;
    private int imageConnected;

    public int getImageConnected() {
        return imageConnected;
    }

    public void setImageConnected(int imageConnected) {
        this.imageConnected = imageConnected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getImageLight() {
        return imageLight;
    }

    public void setImageLight(int imageLight) {
        this.imageLight = imageLight;
    }
}
