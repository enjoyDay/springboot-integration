package com.springbootIntegration.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author liukun
 * @description 身份证验证
 * @date 2019/9/14
 */
public final class IDCardUtil {
    public static final int MAX_MAINLAND_AREACODE = 659004;
    public static final int MIN_MAINLAND_AREACODE = 110000;
    public static final int HONGKONG_AREACODE = 810000;
    public static final int TAIWAN_AREACODE = 710000;
    public static final int MACAO_AREACODE = 820000;
    public static final String regexNum = "^[0-9]*$";
    public static final String regexBirthdayInLeapYear = "^((19[0-9]{2})|(200[0-9])|(201[0-5]))((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))$";
    public static final String regexBirthdayInCommonYear = "^((19[0-9]{2})|(200[0-9])|(201[0-5]))((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))$";
    private static final Set<String> BLACK_SET = new HashSet<String>() {
        {
            this.add("111111111111111");
        }
    };
    static final char[] code = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    static final int[] factor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    private IDCardUtil() {
    }

    public static final boolean isIdentity(String idNumber) {
        if (StringUtils.isBlank(idNumber)) {
            return false;
        } else {
            idNumber = idNumber.trim();
            if (BLACK_SET.contains(idNumber)) {
                return false;
            } else if (!checkIdNumberRegex(idNumber)) {
                return false;
            } else if (!checkIdNumberArea(idNumber.substring(0, 6))) {
                return false;
            } else {
                idNumber = convertFifteenToEighteen(idNumber);
                if (!checkBirthday(idNumber.substring(6, 14))) {
                    return false;
                } else {
                    return checkIdNumberVerifyCode(idNumber);
                }
            }
        }
    }

    public static final Timestamp getBirthdayFromPersonIDCode(String identity) throws Throwable {
        String id = getIDCode(identity);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            Timestamp birthday = new Timestamp(sdf.parse(id.substring(6, 14)).getTime());
            return birthday;
        } catch (ParseException var4) {
            throw new RuntimeException("不是有效的身份证号，请检查");
        }
    }

    public static final String getIDCode(String idCode) throws Throwable {
        if (idCode == null) {
            throw new RuntimeException("输入的身份证号无效，请检查");
        } else if (idCode.length() == 18) {
            if (isIdentity(idCode)) {
                return idCode;
            } else {
                throw new RuntimeException("输入的身份证号无效，请检查");
            }
        } else if (idCode.length() == 15) {
            return convertFifteenToEighteen(idCode);
        } else {
            throw new RuntimeException("输入的身份证号无效，请检查");
        }
    }

    public static final IDCardUtil.Sex getGenderFromPersonIDCode(String identity) throws Throwable {
        String id = getIDCode(identity);
        char sex = id.charAt(16);
        return sex % 2 == 0 ? IDCardUtil.Sex.Female : IDCardUtil.Sex.Male;
    }

    private static boolean checkIdNumberRegex(String idNumber) {
        return Pattern.matches("^([0-9]{17}[0-9Xx])|([0-9]{15})$", idNumber);
    }

    private static boolean checkIdNumberArea(String idNumberArea) {
        int areaCode = Integer.parseInt(idNumberArea);
        if (areaCode != 810000 && areaCode != 820000 && areaCode != 710000) {
            return areaCode <= 659004 && areaCode >= 110000;
        } else {
            return true;
        }
    }

    private static String convertFifteenToEighteen(String idNumber) {
        if (15 != idNumber.length()) {
            return idNumber;
        } else {
            idNumber = idNumber.substring(0, 6) + "19" + idNumber.substring(6, 15);
            idNumber = idNumber + getVerifyCode(idNumber);
            return idNumber;
        }
    }

    private static String getVerifyCode(String idNumber) {
        if (!Pattern.matches("^[0-9]*$", idNumber.substring(0, 17))) {
            return null;
        } else {
            int sum = 0;

            for(int i = 0; i < 17; ++i) {
                sum += Integer.parseInt(String.valueOf(idNumber.charAt(i))) * factor[i];
            }

            return String.valueOf(code[sum % 11]);
        }
    }

    private static boolean checkBirthday(String idNumberBirthdayStr) {
        Integer year = null;

        try {
            year = Integer.valueOf(idNumberBirthdayStr.substring(0, 4));
        } catch (Exception var3) {
            ;
        }

        if (null == year) {
            return false;
        } else {
            return isLeapYear(year) ? Pattern.matches("^((19[0-9]{2})|(200[0-9])|(201[0-5]))((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))$", idNumberBirthdayStr) : Pattern.matches("^((19[0-9]{2})|(200[0-9])|(201[0-5]))((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))$", idNumberBirthdayStr);
        }
    }

    private static boolean isLeapYear(int year) {
        return year % 400 == 0 || year % 100 != 0 && year % 4 == 0;
    }

    private static boolean checkIdNumberVerifyCode(String idNumber) {
        return getVerifyCode(idNumber).equalsIgnoreCase(idNumber.substring(17));
    }

    public static void main(String[] args) throws Throwable {
        System.out.println(getGenderFromPersonIDCode("11010519491231002X"));
        System.out.println(isIdentity("530626199109261396"));
    }

    public static enum Sex {
        Other("未知", 0),
        Male("男", 1),
        Female("女", 2);

        private String name;
        private Integer value;

        private Sex(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }

        public String toString() {
            return this.name;
        }
    }
}
