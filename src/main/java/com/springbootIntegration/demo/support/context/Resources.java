package com.springbootIntegration.demo.support.context;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class Resources {
    public static final ResourceBundle THIRDPARTY = ResourceBundle.getBundle("config/thirdParty");
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap();

    public Resources() {
    }

    public static String getMessage(String key, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle message = (ResourceBundle)MESSAGES.get(locale.getLanguage());
        if (message == null) {
            Map var4 = MESSAGES;
            synchronized(MESSAGES) {
                message = (ResourceBundle)MESSAGES.get(locale.getLanguage());
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
