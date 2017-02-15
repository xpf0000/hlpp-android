package com.X.hlpp;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class HtmlMsgModel implements Serializable {

    private String type = "";
    private String msg = "";
    private String uid = "";
    private String username = "";

    private String url = "";
    private String title = "";
    private String content = "";
    private String pic = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HtmlMsgModel{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
