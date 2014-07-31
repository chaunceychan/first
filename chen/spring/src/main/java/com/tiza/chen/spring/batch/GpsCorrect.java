package com.tiza.chen.spring.batch;

/**
 * Descriptions
 * Created by Chauncey on 2014/7/22.
 */
public class GpsCorrect {
    final static double pi = 3.14159265358979324;
    final static double a = 6378245.0;
    final static double ee = 0.00669342162296594323;

    public static void transform(double wgLat, double wgLon, double[] latlng) {
        if (outOfChina(wgLat, wgLon)) {
            latlng[0] = wgLat;
            latlng[1] = wgLon;
            return;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        latlng[0] = wgLat + dLat;
        latlng[1] = wgLon + dLon;
    }

    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    public static void main(String[] args) {
        double[] array = new double[2];
        transform(30.794850, 121.468265, array);
        System.out.print("加密前:Lat-" + 30.794850 + ",Lng-" + 121.468265);
        System.out.print(";加密后:Lat-" + array[0] + ",Lng-" + array[1]);
        System.out.println(";源程序的加密方式:Lat-" + 30.792683 + ",Lng-" + 121.472779);

        transform(31.980049, 118.742791, array);
        System.out.print("加密前:Lat-" + 31.980049 + ",Lng-" + 118.742791);
        System.out.print(";加密后:Lat-" + array[0] + ",Lng-" + array[1]);
        System.out.println(";源程序的加密方式:Lat-" + 31.978036 + ",Lng-" + 118.748013);

        transform(41.141620, 123.046918, array);
        System.out.print("加密前:Lat-" + 41.141620 + ",Lng-" + 123.046918);
        System.out.print(";加密后:Lat-" + array[0] + ",Lng-" + array[1]);
        System.out.println(";源程序的加密方式:Lat-" + 41.143724 + ",Lng-" + 123.052672);

        transform(32.061916, 119.662151, array);
        System.out.print("加密前:Lat-" + 32.061916 + ",Lng-" + 119.662151);
        System.out.print(";加密后:Lat-" + array[0] + ",Lng-" + array[1]);
        System.out.println(";源程序的加密方式:Lat-" + 32.059790 + ",Lng-" + 119.667145);

        transform(25.078663, 101.552760, array);
        System.out.print("加密前:Lat-" + 25.078663 + ",Lng-" + 101.552760);
        System.out.print(";加密后:Lat-" + array[0] + ",Lng-" + array[1]);
        System.out.println(";源程序的加密方式:Lat-" + 25.075577 + ",Lng-" + 101.554178);

    }
}