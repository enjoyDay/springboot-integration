package com.springbootIntegration.demo.util;

import java.math.BigDecimal;

/**
 * @author liukun
 * @description 精确计算加减乘除，随机数
 * @date 2019/9/14
 */
public final class MathUtil {
    private static int DEF_SCALE = 10;

    private MathUtil() {
    }

    public static final strictfp BigDecimal bigDecimal(Object object) {
        if (object == null) {
            throw new NullPointerException();
        } else if (object instanceof BigDecimal) {
            return (BigDecimal)object;
        } else {
            try {
                BigDecimal result = new BigDecimal(object.toString().replaceAll(",", ""));
                return result;
            } catch (NumberFormatException var3) {
                throw new NumberFormatException("Please give me a numeral.Not " + object);
            }
        }
    }

    public static final strictfp BigDecimal add(Number num1, Number num2) {
        BigDecimal result = bigDecimal(num1).add(bigDecimal(num2));
        return result.setScale(DEF_SCALE, 4);
    }

    public static final strictfp BigDecimal subtract(Number num1, Number num2) {
        BigDecimal result = bigDecimal(num1).subtract(bigDecimal(num2));
        return result.setScale(DEF_SCALE, 4);
    }

    public static final strictfp BigDecimal multiply(Number num1, Number num2) {
        BigDecimal result = bigDecimal(num1).multiply(bigDecimal(num2));
        return result.setScale(DEF_SCALE, 4);
    }

    public static final strictfp BigDecimal divide(Number num1, Number num2) {
        return divide(num1, num2, DEF_SCALE);
    }

    public static final strictfp BigDecimal divide(Number num1, Number num2, Integer scale) {
        if (scale == null) {
            scale = DEF_SCALE;
        }

        num2 = num2 != null && Math.abs(new Double(num2.toString())) != 0.0D ? num2 : 1;
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal result = bigDecimal(num1).divide(bigDecimal(num2), scale, 4);
            return result;
        }
    }

    public static final strictfp BigDecimal round(Number num, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal result = bigDecimal(num).divide(bigDecimal("1"), scale, 4);
            return result;
        }
    }

    public static final strictfp BigDecimal getRandom(double start, double end) {
        return new BigDecimal(start + Math.random() * (end - start));
    }

    public static final strictfp void main(String[] args) {
        System.out.println(add(1.000001D, 2.1D));
    }
}