package com.example.customview.bean;

/**
 * Created by wuming on 16/10/19.
 */

public class WeatherBean extends BaseResp {
    private int tmp;
    private String city;

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "tmp=" + tmp +
                ", city='" + city + '\'' +
                '}';
    }
}
