package com.tiza.chen.spring.bean;

import com.google.common.base.Objects;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/15.
 */
public class Region implements Area {
    /**
     * 顶点的个数
     */
    private int nPoiCount;
    /**
     * 顺时针，顶点的位置
     */
    private Point[] pts;

    public Point[] getPts() {
        return pts;
    }

    public void setPts(Point[] pts) {
        this.pts = pts;
        this.nPoiCount = pts.length;
    }

    @Override
    public int isPointIn(Point point) {
        return 0;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("nPoiCount", nPoiCount).toString();
    }
}
