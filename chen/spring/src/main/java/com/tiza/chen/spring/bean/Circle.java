package com.tiza.chen.spring.bean;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Circle implements Area, Serializable {
    private double lng;
    private double lat;
    private double radius;

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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public int isPointIn(Point point) {
        return 0;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("lng", lng).add("lat", lat).add("radius", radius).toString();
    }
}
