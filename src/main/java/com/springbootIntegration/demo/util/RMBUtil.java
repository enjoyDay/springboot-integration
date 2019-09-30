package com.springbootIntegration.demo.util;

/**
 * @author liukun
 * @description 数字转成大写人民币
 * @date 2019/9/14
 */
public final class RMBUtil {
    private static String[] HanDigiStr = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static String[] HanDiviStr = new String[]{"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};

    private RMBUtil() {
    }

    public static final String numToRMBStr(double val) {
        String signStr = "";
        String tailStr = "";
        if (val < 0.0D) {
            val = -val;
            signStr = "负";
        }

        if (val <= 1.0E14D && val >= -1.0E14D) {
            long temp = Math.round(val * 100.0D);
            long integer = temp / 100L;
            long fraction = temp % 100L;
            int jiao = (int)fraction / 10;
            int fen = (int)fraction % 10;
            if (jiao == 0 && fen == 0) {
                tailStr = "整";
            } else {
                tailStr = HanDigiStr[jiao];
                if (jiao != 0) {
                    tailStr = tailStr + "角";
                }

                if (integer == 0L && jiao == 0) {
                    tailStr = "";
                }

                if (fen != 0) {
                    tailStr = tailStr + HanDigiStr[fen] + "分";
                }
            }

            return signStr + positiveIntegerToHanStr(String.valueOf(integer)) + "元" + tailStr;
        } else {
            return "数值位数过大!";
        }
    }

    private static String positiveIntegerToHanStr(String numStr) {
        String rmbStr = "";
        boolean lastzero = false;
        boolean hasvalue = false;
        int len = numStr.length();
        if (len > 15) {
            return "数值过大!";
        } else {
            for(int i = len - 1; i >= 0; --i) {
                if (numStr.charAt(len - i - 1) != ' ') {
                    int n = numStr.charAt(len - i - 1) - 48;
                    if (n < 0 || n > 9) {
                        return "输入含非数字字符!";
                    }

                    if (n != 0) {
                        if (lastzero) {
                            rmbStr = rmbStr + HanDigiStr[0];
                        }

                        if (n != 1 || i % 4 != 1 || i != len - 1) {
                            rmbStr = rmbStr + HanDigiStr[n];
                        }

                        rmbStr = rmbStr + HanDiviStr[i];
                        hasvalue = true;
                    } else if (i % 8 == 0 || i % 8 == 4 && hasvalue) {
                        rmbStr = rmbStr + HanDiviStr[i];
                    }

                    if (i % 8 == 0) {
                        hasvalue = false;
                    }

                    lastzero = n == 0 && i % 4 != 0;
                }
            }

            if (rmbStr.length() == 0) {
                return HanDigiStr[0];
            } else {
                return rmbStr;
            }
        }
    }
}
