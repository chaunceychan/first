package com.tiza.chen.spring.bean;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Point implements Serializable {
    private double lng;
    private double lat;
    private String pro;
    private String city;
    private String area;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

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


    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("lng", lng).add("lat", lat).toString();
    }
}
