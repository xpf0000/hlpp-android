package com.X.hlpp;

import java.io.Serializable;

/**
 * Created by X on 2017/1/18.
 */

public class ImageModel implements Serializable {

    private String  url = "";
    private String  id = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ImageModel{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
