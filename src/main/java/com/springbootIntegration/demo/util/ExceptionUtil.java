package com.springbootIntegration.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author liukun
 * @description 异常工具类
 * @date 2019/9/14
 */
public class ExceptionUtil {
    public ExceptionUtil() {
    }

    public static RuntimeException unchecked(Exception e) {
        return e instanceof RuntimeException ? (RuntimeException)e : new RuntimeException(e);
    }

    public static String getStackTraceAsString(Throwable e) {
        if (e == null) {
            return "";
        } else {
            //通过stringbuffer创建一个缓冲
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.toString();
        }
    }

    @SafeVarargs
    public static boolean isCausedBy(Exception ex, Class... causeExceptionClasses) {
        for(Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            Class[] var3 = causeExceptionClasses;
            int var4 = causeExceptionClasses.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Class<? extends Exception> causeClass = var3[var5];
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Throwable getThrowable(HttpServletRequest request) {
        Throwable ex = null;
        if (request.getAttribute("exception") != null) {
            ex = (Throwable)request.getAttribute("exception");
        } else if (request.getAttribute("javax.servlet.error.exception") != null) {
            ex = (Throwable)request.getAttribute("javax.servlet.error.exception");
        }

        return ex;
    }
}
