package com.springbootIntegration.demo.util;

import java.math.BigDecimal;

/**
 * 位置工具类
 */
public class LocationUtil {
    private static final double EARTH_RADIUS = 6378137.0D;

    public LocationUtil() {
    }

    //角度转换为弧度
    private static double rad(double d) {
        return d * 3.141592653589793D / 180.0D;
    }
    //两点之间距离
    public static Double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));
        s *= 6378137.0D;
        return s;
    }

    public static Double getDistance(BigDecimal lon1, BigDecimal lat1, BigDecimal lon2, BigDecimal lat2) {
        return getDistance(lon1.doubleValue(), lat1.doubleValue(), lon2.doubleValue(), lat2.doubleValue());
    }
}