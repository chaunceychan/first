package com.tiza.chen.spring.bean;

import com.google.common.base.Objects;

import java.util.List;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Group implements Area {
    private List<Division> list;

    public List<Division> getList() {
        return list;
    }

    public void setList(List<Division> list) {
        this.list = list;
    }

    @Override
    public int isPointIn(Point point) {
        return 0;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("list", list.size()).toString();
    }
}
