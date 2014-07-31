package com.tiza.chen.spring.bean;

import java.io.Serializable;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Division implements Serializable {
    private String pro;
    private String city;
    private String area;

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
