package com.springbootIntegration.demo.util;

import com.alibaba.fastjson.JSON;
import com.springbootIntegration.demo.support.exception.DataParseException;
import com.springbootIntegration.demo.support.exception.DataParseException;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * 将各种类型数据转换成对象
 */
public final class TypeParseUtil {
    static final String message = "Could not convert %1$s to %2$s";
    static final String support = "Convert from %1$s to %2$s not currently supported";

    private TypeParseUtil() {
    }

    public static final Object convert(Object value, Class<?> type, String format) {
        return convert(value, type.getName(), format);
    }

    public static final Object convert(Object value, String type, String format) {
        Locale locale = Locale.getDefault();
        if (value == null) {
            return null;
        } else if (!value.getClass().getName().equalsIgnoreCase(type) && !value.getClass().getSimpleName().equalsIgnoreCase(type)) {
            if (!"Object".equalsIgnoreCase(type) && !"java.lang.Object".equals(type)) {
                if (value instanceof String) {
                    return string2Object(value, type, format, locale);
                } else if (value instanceof BigDecimal) {
                    return decimal2Obj(value, type, locale);
                } else if (value instanceof Double) {
                    return double2Obj(value, type, locale);
                } else if (value instanceof Float) {
                    return float2Obj(value, type, locale);
                } else if (value instanceof Long) {
                    return long2Obj(value, type, locale);
                } else if (value instanceof Integer) {
                    return intr2Obj(value, type, locale);
                } else if (value instanceof Date) {
                    return date2Obj(value, type, format);
                } else if (value instanceof java.sql.Date) {
                    return sqlDate2Obj(value, type, format);
                } else if (value instanceof Timestamp) {
                    return time2Obj(value, type, format);
                } else if (value instanceof Boolean) {
                    return bool2Obj(value, type);
                } else if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
                    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", value.getClass().getName(), type));
                } else {
                    return value.toString();
                }
            } else {
                return value;
            }
        } else {
            return value;
        }
    }

    private static Object bool2Obj(Object value, String type) {
        String fromType = "Boolean";
        Boolean bol = (Boolean)value;
        if (!"Boolean".equalsIgnoreCase(type) && !"java.lang.Boolean".equalsIgnoreCase(type)) {
            if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
                if (!"Integer".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                    throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                } else {
                    return bol ? new Integer(1) : new Integer(0);
                }
            } else {
                return bol.toString();
            }
        } else {
            return bol;
        }
    }

    private static Object time2Obj(Object value, String type, String format) {
        String fromType = "Timestamp";
        Timestamp tme = (Timestamp)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"Date".equalsIgnoreCase(type) && !"java.util.Date".equalsIgnoreCase(type)) {
                if ("java.sql.Date".equalsIgnoreCase(type)) {
                    return new java.sql.Date(tme.getTime());
                } else if (!"Time".equalsIgnoreCase(type) && !"java.sql.Time".equalsIgnoreCase(type)) {
                    if (!"Timestamp".equalsIgnoreCase(type) && !"java.sql.Timestamp".equalsIgnoreCase(type)) {
                        throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                    } else {
                        return value;
                    }
                } else {
                    return new Time(tme.getTime());
                }
            } else {
                return new Date(tme.getTime());
            }
        } else if (format != null && format.length() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(tme.getTime()));
        } else {
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(tme).toString();
        }
    }

    private static Object sqlDate2Obj(Object value, String type, String format) {
        String fromType = "Date";
        java.sql.Date dte = (java.sql.Date)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"Date".equalsIgnoreCase(type) && !"java.util.Date".equalsIgnoreCase(type)) {
                if ("java.sql.Date".equalsIgnoreCase(type)) {
                    return value;
                } else if (!"Time".equalsIgnoreCase(type) && !"java.sql.Time".equalsIgnoreCase(type)) {
                    if (!"Timestamp".equalsIgnoreCase(type) && !"java.sql.Timestamp".equalsIgnoreCase(type)) {
                        throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                    } else {
                        return new Timestamp(dte.getTime());
                    }
                } else {
                    throw new DataParseException("Conversion from " + fromType + " to " + type + " not currently supported");
                }
            } else {
                return new Date(dte.getTime());
            }
        } else if (format != null && format.length() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(dte.getTime()));
        } else {
            return dte.toString();
        }
    }

    private static Object date2Obj(Object value, String type, String format) {
        String fromType = "Date";
        Date dte = (Date)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"Date".equalsIgnoreCase(type) && !"java.util.Date".equalsIgnoreCase(type)) {
                if ("java.sql.Date".equalsIgnoreCase(type)) {
                    return new java.sql.Date(dte.getTime());
                } else if (!"Time".equalsIgnoreCase(type) && !"java.sql.Time".equalsIgnoreCase(type)) {
                    if (!"Timestamp".equalsIgnoreCase(type) && !"java.sql.Timestamp".equalsIgnoreCase(type)) {
                        throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                    } else {
                        return new Timestamp(dte.getTime());
                    }
                } else {
                    return new Time(dte.getTime());
                }
            } else {
                return value;
            }
        } else if (format != null && format.length() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(dte);
        } else {
            return dte.toString();
        }
    }

    private static Object intr2Obj(Object value, String type, Locale locale) {
        String fromType = "Integer";
        Integer intgr = (Integer)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"Double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                if (!"Float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                    if (!"BigDecimal".equalsIgnoreCase(type) && !"java.math.BigDecimal".equalsIgnoreCase(type)) {
                        if (!"Long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                            if (!"Integer".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                                throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                            } else {
                                return value;
                            }
                        } else {
                            return new Long(intgr.toString());
                        }
                    } else {
                        String str = intgr.toString();
                        BigDecimal retBig = new BigDecimal(intgr.toString());
                        int iscale = str.indexOf(".");
                        int keylen = str.length();
                        if (iscale > -1) {
                            iscale = keylen - (iscale + 1);
                            return retBig.setScale(iscale, 5);
                        } else {
                            return retBig.setScale(0, 5);
                        }
                    }
                } else {
                    return new Float(intgr.toString());
                }
            } else {
                return new Double(intgr.toString());
            }
        } else {
            return getNf(locale).format(intgr.toString());
        }
    }

    private static Object long2Obj(Object value, String type, Locale locale) {
        String fromType = "Long";
        Long lng = (Long)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"Double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                if (!"Float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                    if (!"BigDecimal".equalsIgnoreCase(type) && !"java.math.BigDecimal".equalsIgnoreCase(type)) {
                        if (!"Long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                            if (!"Integer".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                                if (!"Date".equalsIgnoreCase(type) && !"java.util.Date".equalsIgnoreCase(type)) {
                                    if ("java.sql.Date".equalsIgnoreCase(type)) {
                                        return new java.sql.Date(lng);
                                    } else if (!"Time".equalsIgnoreCase(type) && !"java.sql.Time".equalsIgnoreCase(type)) {
                                        if (!"Timestamp".equalsIgnoreCase(type) && !"java.sql.Timestamp".equalsIgnoreCase(type)) {
                                            throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                                        } else {
                                            return new Timestamp(lng);
                                        }
                                    } else {
                                        return new Time(lng);
                                    }
                                } else {
                                    return new Date(lng);
                                }
                            } else {
                                return new Integer(lng.toString());
                            }
                        } else {
                            return value;
                        }
                    } else {
                        return new BigDecimal(lng.toString());
                    }
                } else {
                    return new Float(lng.toString());
                }
            } else {
                return new Double(lng.toString());
            }
        } else {
            return getNf(locale).format(lng.toString());
        }
    }

    private static Object float2Obj(Object value, String type, Locale locale) {
        String fromType = "Float";
        Float flt = (Float)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"BigDecimal".equalsIgnoreCase(type) && !"java.math.BigDecimal".equalsIgnoreCase(type)) {
                if (!"Double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                    if (!"Float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                        if (!"Long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                            if (!"Integer".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                                throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                            } else {
                                return new Integer(flt.toString());
                            }
                        } else {
                            return new Long(flt.toString());
                        }
                    } else {
                        return value;
                    }
                } else {
                    return new Double(flt.toString());
                }
            } else {
                return new BigDecimal(flt.toString());
            }
        } else {
            return getNf(locale).format(flt.toString());
        }
    }

    private static Object double2Obj(Object value, String type, Locale locale) {
        String fromType = "Double";
        Double dbl = (Double)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"Double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                if (!"Float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                    if (!"Long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                        if (!"Integer".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                            if (!"BigDecimal".equalsIgnoreCase(type) && !"java.math.BigDecimal".equalsIgnoreCase(type)) {
                                throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                            } else {
                                return new BigDecimal(dbl.toString());
                            }
                        } else {
                            return new Integer(dbl.toString());
                        }
                    } else {
                        return new Long(dbl.toString());
                    }
                } else {
                    return new Float(dbl.toString());
                }
            } else {
                return value;
            }
        } else {
            return getNf(locale).format(dbl.toString());
        }
    }

    private static Object decimal2Obj(Object value, String type, Locale locale) {
        String fromType = "BigDecimal";
        BigDecimal bigD = (BigDecimal)value;
        if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
            if (!"BigDecimal".equalsIgnoreCase(type) && !"java.math.BigDecimal".equalsIgnoreCase(type)) {
                if (!"Double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                    if (!"Float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                        if (!"Long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                            if (!"Integer".equals(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                                throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                            } else {
                                return new Integer(bigD.toString());
                            }
                        } else {
                            return new Long(bigD.toString());
                        }
                    } else {
                        return new Float(bigD.toString());
                    }
                } else {
                    return new Double(bigD.toString());
                }
            } else {
                return value;
            }
        } else {
            return getNf(locale).format(bigD.toString());
        }
    }

    private static Object string2Object(Object value, String type, String format, Locale locale) {
        String fromType = "String";
        String str = (String)value;

        try {
            if (!"String".equalsIgnoreCase(type) && !"java.lang.String".equalsIgnoreCase(type)) {
                if (str.length() == 0) {
                    return null;
                } else if (!"Boolean".equalsIgnoreCase(type) && !"java.lang.Boolean".equals(type)) {
                    Number tempNum;
                    if (!"Double".equalsIgnoreCase(type) && !"java.lang.Double".equalsIgnoreCase(type)) {
                        if (!"BigDecimal".equalsIgnoreCase(type) && !"java.math.BigDecimal".equalsIgnoreCase(type)) {
                            if (!"Float".equalsIgnoreCase(type) && !"java.lang.Float".equalsIgnoreCase(type)) {
                                NumberFormat nf;
                                Number df;
                                if (!"Long".equalsIgnoreCase(type) && !"java.lang.Long".equalsIgnoreCase(type)) {
                                    if (!"Integer".equalsIgnoreCase(type) && !"java.lang.Integer".equalsIgnoreCase(type)) {
                                        SimpleDateFormat sdf;
                                        if (!"Date".equalsIgnoreCase(type) && !"java.util.Date".equalsIgnoreCase(type)) {
                                            DateFormat dateFormat;
                                            Date fieldDate;
                                            if ("java.sql.Date".equalsIgnoreCase(type)) {
                                                if (format != null && format.length() != 0) {
                                                    sdf = new SimpleDateFormat(format);
                                                    fieldDate = sdf.parse(str);
                                                    return new java.sql.Date(fieldDate.getTime());
                                                } else {
                                                    try {
                                                        return java.sql.Date.valueOf(str);
                                                    } catch (Exception var11) {
                                                        try {
                                                            df = null;
                                                            if (locale != null) {
                                                                dateFormat = DateFormat.getDateInstance(3, locale);
                                                            } else {
                                                                dateFormat = DateFormat.getDateInstance(3);
                                                            }

                                                            fieldDate = dateFormat.parse(str);
                                                            return new java.sql.Date(fieldDate.getTime());
                                                        } catch (ParseException var10) {
                                                            throw new DataParseException(String.format("Could not convert %1$s to %2$s", str, type), var11);
                                                        }
                                                    }
                                                }
                                            } else if (!"Timestamp".equalsIgnoreCase(type) && !"java.sql.Timestamp".equalsIgnoreCase(type)) {
                                                throw new DataParseException(String.format("Convert from %1$s to %2$s not currently supported", fromType, type));
                                            } else {
                                                if (str.length() == 10) {
                                                    str = str + " 00:00:00";
                                                }

                                                if (format != null && format.length() != 0) {
                                                    try {
                                                        sdf = new SimpleDateFormat(format);
                                                        fieldDate = sdf.parse(str);
                                                        return new Timestamp(fieldDate.getTime());
                                                    } catch (ParseException var12) {
                                                        throw new DataParseException(String.format("Could not convert %1$s to %2$s", str, type), var12);
                                                    }
                                                } else {
                                                    try {
                                                        return Timestamp.valueOf(str);
                                                    } catch (Exception var13) {
                                                        try {
                                                            df = null;
                                                            if (locale != null) {
                                                                dateFormat = DateFormat.getDateTimeInstance(3, 3, locale);
                                                            } else {
                                                                dateFormat = DateFormat.getDateTimeInstance(3, 3);
                                                            }

                                                            fieldDate = dateFormat.parse(str);
                                                            return new Timestamp(fieldDate.getTime());
                                                        } catch (ParseException var9) {
                                                            throw new DataParseException(String.format("Could not convert %1$s to %2$s", str, type), var13);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (format == null || format.length() == 0) {
                                                String separator = String.valueOf(str.charAt(4));
                                                StringBuilder pattern;
                                                if (separator.matches("\\d*")) {
                                                    pattern = new StringBuilder("yyyyMMdd HH:mm:ss");
                                                    format = pattern.substring(0, str.length());
                                                } else {
                                                    pattern = (new StringBuilder("yyyy")).append(separator).append("MM").append(separator).append("dd HH:mm:ss");
                                                    format = pattern.substring(0, str.length());
                                                }
                                            }

                                            sdf = new SimpleDateFormat(format);
                                            return sdf.parse(str);
                                        }
                                    } else {
                                        nf = getNf(locale);
                                        nf.setMaximumFractionDigits(0);
                                        df = nf.parse(str.replaceAll(",", ""));
                                        return new Integer(df.toString());
                                    }
                                } else {
                                    nf = getNf(locale);
                                    nf.setMaximumFractionDigits(0);
                                    df = nf.parse(str.replaceAll(",", ""));
                                    return new Long(df.toString());
                                }
                            } else {
                                tempNum = getNf(locale).parse(str.replaceAll(",", ""));
                                return new Float(tempNum.toString());
                            }
                        } else {
                            BigDecimal retBig = new BigDecimal(str.replaceAll(",", ""));
                            int iscale = str.indexOf(".");
                            int keylen = str.length();
                            if (iscale > -1) {
                                iscale = keylen - (iscale + 1);
                                return retBig.setScale(iscale, 5);
                            } else {
                                return retBig.setScale(0, 5);
                            }
                        }
                    } else {
                        tempNum = getNf(locale).parse(str.replaceAll(",", ""));
                        return new Double(tempNum.toString());
                    }
                } else {
                    return str.equalsIgnoreCase("TRUE") ? new Boolean(true) : new Boolean(false);
                }
            } else {
                return value;
            }
        } catch (Exception var14) {
            throw new DataParseException(String.format("Could not convert %1$s to %2$s", str, type), var14);
        }
    }

    private static NumberFormat getNf(Locale locale) {
        NumberFormat nf = null;
        if (locale == null) {
            nf = NumberFormat.getNumberInstance();
        } else {
            nf = NumberFormat.getNumberInstance(locale);
        }

        nf.setGroupingUsed(false);
        return nf;
    }

    public static final Boolean convertToBoolean(Object obj) {
        return (Boolean)convert(obj, (String)"Boolean", (String)null);
    }

    public static final Integer convertToInteger(Object obj) {
        return (Integer)convert(obj, (String)"Integer", (String)null);
    }

    public static final String convertToString(Object obj) {
        return (String)convert(obj, (String)"String", (String)null);
    }

    public static final String convertToString(Object obj, String defaultValue) {
        Object s = convert(obj, (String)"String", (String)null);
        return s != null ? (String)s : "";
    }

    public static final Long convertToLong(Object obj) {
        return (Long)convert(obj, (String)"Long", (String)null);
    }

    public static final Double convertToDouble(Object obj) {
        return (Double)convert(obj, (String)"Double", (String)null);
    }

    public static final Double convertToFloat(Object obj) {
        return (Double)convert(obj, (String)"Float", (String)null);
    }

    public static final BigDecimal convertToBigDecimal(Object obj, int scale) {
        return ((BigDecimal)convert(obj, (String)"BigDecimal", (String)null)).setScale(scale, 5);
    }

    public static final Date convertToDate(Object obj, String format) {
        return (Date)convert(obj, "Date", format);
    }

    public static final java.sql.Date convertToSqlDate(Object obj, String format) {
        return (java.sql.Date)convert(obj, "java.sql.Date", format);
    }

    public static final Timestamp convertToTimestamp(Object obj, String format) {
        return (Timestamp)convert(obj, "Timestamp", format);
    }

    public static String[] convertToStringArray(Object object) {
        if (object instanceof List) {
            List list = (List)object;
            String[] ss = new String[list.size()];
            IntStream.range(0, list.size()).forEach((i) -> {
                ss[i] = JSON.toJSONString(list.get(i));
            });
            return ss;
        } else {
            return new String[]{JSON.toJSONString(object)};
        }
    }
}