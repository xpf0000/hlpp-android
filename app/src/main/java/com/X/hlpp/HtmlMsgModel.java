package com.X.hlpp;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class HtmlMsgModel implements Serializable {

    private String type = "";
    private String msg = "";

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
                '}';
    }
}
