package com.wuzhanglao.niubi.bean;

/**
 * Created by wuming on 2016/12/6.
 */

public class TextBean {
    private String title;
    private String subTitle;

    public TextBean() {

    }

    public TextBean(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
