package com.springbootIntegration.demo.support.context;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class Resources {
    //注意下面这句话由于没有创建配置文件，但是静态变量在加载时候还要去优先加载，所以会抛出异常ExceptionInInitializerError
//    public static final ResourceBundle THIRDPARTY = ResourceBundle.getBundle("config/thirdParty");
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap();

    public Resources() {
    }

    public static String getMessage(String key, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            Map var4 = MESSAGES;
            synchronized(MESSAGES) {
                message = MESSAGES.get(locale.getLanguage());
                if (message == null) {
                    message = ResourceBundle.getBundle("i18n/messages", locale);
                    MESSAGES.put(locale.getLanguage(), message);
                }
            }
        }

        return params != null && params.length > 0 ? String.format(message.getString(key), params) : message.getString(key);
    }

    public static void flushMessage() {
        MESSAGES.clear();
    }
}
