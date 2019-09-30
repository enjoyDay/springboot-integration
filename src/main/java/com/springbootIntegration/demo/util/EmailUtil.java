package com.springbootIntegration.demo.util;

import com.springbootIntegration.demo.support.email.Email;
import com.springbootIntegration.demo.support.email.EmailSender;
import com.springbootIntegration.demo.support.email.Email;
import com.springbootIntegration.demo.support.email.EmailSender;

/**
 * @author liukun
 * @description 发送邮件工具类
 * @date 2019/9/14
 */
public final class EmailUtil {
    private EmailUtil() {
    }

    public static final boolean sendEmail(Email email) {
        EmailSender sender = new EmailSender(email.getHost(), email.getPort(), email.isSSL());
        if (!sender.setNamePass(email.getName(), email.getPassword(), email.getUserKey())) {
            return false;
        } else if (!sender.setFrom(email.getFrom())) {
            return false;
        } else if (!sender.setTo(email.getSendTo())) {
            return false;
        } else if (email.getCopyTo() != null && !sender.setCopyTo(email.getCopyTo())) {
            return false;
        } else if (!sender.setSubject(email.getTopic())) {
            return false;
        } else if (!sender.setBody(email.getBody())) {
            return false;
        } else {
            if (email.getFileAffix() != null) {
                for (int i = 0; i < email.getFileAffix().length; ++i) {
                    if (!sender.addFileAffix(email.getFileAffix()[i])) {
                        return false;
                    }
                }
            }

            return sender.sendout();
        }
    }
}

