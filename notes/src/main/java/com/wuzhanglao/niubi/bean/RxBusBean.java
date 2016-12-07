package com.wuzhanglao.niubi.bean;

import com.google.gson.Gson;

/**
 * Created by wuming on 2016/12/2.
 */

public class RxBusBean {
    private String from;
    private String to;
    private String content;

    public RxBusBean() {

    }

    public RxBusBean(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        Gson gson = new Gson();
    }
}
