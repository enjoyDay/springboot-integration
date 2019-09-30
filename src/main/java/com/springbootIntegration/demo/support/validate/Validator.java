package com.springbootIntegration.demo.support.validate;

import com.springbootIntegration.demo.support.exception.ValidateException;
import com.springbootIntegration.demo.util.IDCardUtil;
import com.springbootIntegration.demo.support.exception.ValidateException;
import com.springbootIntegration.demo.util.DataUtil;
import com.springbootIntegration.demo.util.IDCardUtil;

import java.lang.reflect.Field;

/**
 * @author liukun
 * @description 正则注解类的反射类，用于解释注解，依赖于IDCardUtil类
 * @date 2019/9/14
 */
public class Validator {
    private Validator() {
    }

    public static void valid(Object object) throws Exception {
        //获取类
        Class<? extends Object> clazz = object.getClass();
        //获取声明的类成员变量，包括public,private,protected，但是不包括继承的
        Field[] fields = clazz.getDeclaredFields();
        Field[] var3 = fields;
        int fieldLength = fields.length;

        for (int i = 0; i < fieldLength; i++) {
            Field field = var3[i];
            //表示反射的对象抑制java语言检查
            //当字段是private 时，设置为true才能访问
            field.setAccessible(true);
            //获取成员变量的注释
            Validate validate = field.getAnnotation(Validate.class);
            if (validate != null) {
                validate(validate, field.get(object), field.getName());
            }
        }
    }

    public static void validate(Validate validate, Object value, String filedName) throws ValidateException {
        String description = DataUtil.isEmpty(validate.desc()) ? filedName : validate.desc();
        if (!validate.nullable() || !DataUtil.isEmpty(value)) {
            if (!validate.nullable() && DataUtil.isEmpty(value)) {
                throw new ValidateException(description + "不能为空");
            } else if (validate.min() != 0.0D && new Double(value.toString()) < validate.min()) {
                throw new ValidateException(description + "不能小于" + validate.min());
            } else if (validate.max() != 0.0D && new Double(value.toString()) > validate.max()) {
                throw new ValidateException(description + "不能大于" + validate.max());
            } else if (validate.minLength() != 0 && value.toString().length() < validate.minLength()) {
                throw new ValidateException(description + "长度不能小于" + validate.minLength());
            } else if (validate.maxLength() != 0 && value.toString().length() > validate.maxLength()) {
                throw new ValidateException(description + "长度不能超过" + validate.maxLength());
            } else {
                //值不为空
                validate(validate.type(), value, description);
                if (DataUtil.isNotEmpty(validate.regex()) && !value.toString().matches(validate.regex())) {
                    throw new ValidateException(description + "格式不正确");
                }
            }
        }
    }

    public static void validate(RegexType type, Object value, String description) throws ValidateException {
        switch (type) {
            case NONE:
            default:
                break;
            case IDCARD:
                if (!IDCardUtil.isIdentity(value.toString())) {
                    throw new ValidateException(description + "格式不正确");
                }
                break;
            case DATE:
                if (!value.toString().matches(RegexType.DATE.value())) {
                    throw new ValidateException(description + "格式不正确");
                }
                break;
            case SPECIALCHAR:
                if (DataUtil.hasSpecialChar(value.toString())) {
                    throw new ValidateException(description + "不能含有特殊字符");
                }
                break;
            case PASSWORD:
                if (!DataUtil.isPassword(value.toString())) {
                    throw new ValidateException(description + "必须是大小写字母和数字的组合，长度在8-16之间");
                }
                break;
            case CHINESE:
                if (!DataUtil.isChinese(value.toString())) {
                    throw new ValidateException(description + "只能输入中文字符");
                }
                break;
            case NONECHINESE:
                if (DataUtil.isChinese2(value.toString())) {
                    throw new ValidateException(description + "不能含有中文字符");
                }
                break;
            case EMAIL:
                if (!DataUtil.isEmail(value.toString())) {
                    throw new ValidateException(description + "格式不正确");
                }
                break;
            case IP:
                if (!DataUtil.isIp(value.toString())) {
                    throw new ValidateException(description + "格式不正确");
                }
                break;
            case NUMBER:
                if (!DataUtil.isNumber(value.toString())) {
                    throw new ValidateException(description + "不是数字");
                }
                break;
            case PHONE:
                if (!DataUtil.isPhone(value.toString())) {
                    throw new ValidateException(description + "格式不正确");
                }
                break;
            case TELEPHONE:
                if (!DataUtil.isTelephone(value.toString())) {
                    throw new ValidateException(description + "格式不正确");
                }
        }
    }
}
