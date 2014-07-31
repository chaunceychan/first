package com.tiza.chen.spring.bean;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Storage implements Serializable {
    private int id;
    private String name;
    private int type;
    private Area area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).add("type", type).add("area", area).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Storage)) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
