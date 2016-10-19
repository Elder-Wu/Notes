package com.example.customview.bean;

/**
 * Created by wuming on 16/10/19.
 */

public class BaseResp {
    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
