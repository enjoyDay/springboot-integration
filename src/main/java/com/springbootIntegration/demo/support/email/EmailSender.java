package com.springbootIntegration.demo.support.email;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.springbootIntegration.demo.support.context.Resources ;
import com.springbootIntegration.demo.util.PropertiesUtil ;
/**
 * @author liukun
 * @description
 * @date 2019/9/14
 */
public final class EmailSender {
    private final Logger logger = LogManager.getLogger();
    private MimeMessage mimeMsg;
    private Session session;
    private Properties props;
    private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private String username = "";
    private String password = "";
    private String userkey = "";
    private boolean isSSL;
    private Multipart mp;

    public EmailSender(String host, String port, boolean isSSL) {
        try {
            this.isSSL = isSSL;
            if (host == null || host.trim().equals("")) {
                host = PropertiesUtil.getString("email.smtp.host");
            }

            this.logger.info(Resources.getMessage("EMAIL.SET_HOST", new Object[0]), host);
            if (this.props == null) {
                this.props = System.getProperties();
            }

            this.props.put("mail.smtp.host", host);
            if (port == null || port.trim().equals("")) {
                port = PropertiesUtil.getString("email.smtp.port");
            }

            if (port != null && !port.trim().equals("")) {
                this.props.put("mail.smtp.port", "25");
            } else {
                this.props.put("mail.smtp.port", port);
            }

            if ("smtp.gmail.com".equals(host) || isSSL) {
                this.props.put("mail.smtp.port", "465");
                this.props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                this.props.setProperty("mail.smtp.socketFactory.fallback", "false");
                this.props.setProperty("mail.smtp.socketFactory.port", "465");
            }

            if (!this.createMimeMessage()) {
                throw new RuntimeException("创建MIME邮件对象和会话失败");
            }
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public boolean setNamePass(String name, String pass, String key) {
        if (name == null || name.trim().equals("")) {
            name = PropertiesUtil.getString("email.user.name");
        }

        if (pass == null || pass.trim().equals("")) {
            pass = PropertiesUtil.getString("email.user.password");
        }

        this.username = name;
        this.password = pass;
        this.userkey = key;
        this.setNeedAuth();
        return this.createMimeMessage();
    }

    private void setNeedAuth() {
        if (this.props == null) {
            this.props = System.getProperties();
        }

        if (this.userkey == null || this.userkey.trim().equals("")) {
            this.userkey = PropertiesUtil.getString("email.authorisation.code");
        }

        if (this.userkey != null && !this.userkey.trim().equals("")) {
            this.props.setProperty("mail.smtp.auth", "true");
            this.logger.info(Resources.getMessage("EMAIL.SET_AUTH", new Object[0]), "true");
        } else {
            this.props.setProperty("mail.smtp.auth", "false");
            this.logger.info(Resources.getMessage("EMAIL.SET_AUTH", new Object[0]), "false");
        }

    }

    private boolean createMimeMessage() {
        if (this.session == null) {
            try {
                this.session = Session.getInstance(this.props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        if (EmailSender.this.userkey != null && !"".equals(EmailSender.this.userkey.trim())) {
                            return EmailSender.this.isSSL ? new PasswordAuthentication(EmailSender.this.username, EmailSender.this.password) : new PasswordAuthentication(EmailSender.this.username, EmailSender.this.userkey);
                        } else {
                            return null;
                        }
                    }
                });
            } catch (Exception var3) {
                this.logger.error(Resources.getMessage("EMAIL.ERROR_TALK", new Object[0]), var3.getLocalizedMessage());
                return false;
            }
        }

        if (this.mimeMsg == null) {
            try {
                this.mimeMsg = new MimeMessage(this.session);
                this.mp = new MimeMultipart();
                return true;
            } catch (Exception var2) {
                this.logger.error(Resources.getMessage("EMAIL.ERROR_MIME", new Object[0]), var2.getLocalizedMessage());
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean setSubject(String mailSubject) {
        this.logger.info(Resources.getMessage("EMAIL.SET_SUBJECT", new Object[0]), mailSubject);

        try {
            this.mimeMsg.setSubject(mailSubject, "UTF-8");
            return true;
        } catch (Exception var3) {
            this.logger.error(Resources.getMessage("EMAIL.ERROR_SUBJECT", new Object[0]), var3);
            return false;
        }
    }

    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=UTF-8");
            this.mp.addBodyPart(bp);
            return true;
        } catch (Exception var3) {
            this.logger.error(Resources.getMessage("EMAIL.ERROR_BODY", new Object[0]), var3);
            return false;
        }
    }

    public boolean addFileAffix(String filename) {
        this.logger.info(Resources.getMessage("EMAIL.ADD_ATTEND", new Object[0]), filename);

        try {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(MimeUtility.encodeText(fileds.getName()));
            this.mp.addBodyPart(bp);
            return true;
        } catch (Exception var4) {
            this.logger.error(filename, var4);
            return false;
        }
    }

    public boolean setFrom(String from) {
        if (from == null || from.trim().equals("")) {
            from = PropertiesUtil.getString("email.send.from");
        }

        try {
            String[] f = from.split(",");
            if (f.length > 1) {
                from = MimeUtility.encodeText(f[0]) + "<" + f[1] + ">";
            }

            this.mimeMsg.setFrom(new InternetAddress(from));
            return true;
        } catch (Exception var3) {
            this.logger.error(var3.getLocalizedMessage());
            return false;
        }
    }

    public boolean setTo(String to) {
        if (to == null) {
            return false;
        } else {
            this.logger.info(Resources.getMessage("EMAIL.SET_TO", new Object[0]), to);

            try {
                this.mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                return true;
            } catch (Exception var3) {
                this.logger.error(var3.getLocalizedMessage());
                return false;
            }
        }
    }

    public boolean setCopyTo(String copyto) {
        if (copyto == null) {
            return false;
        } else {
            this.logger.info(Resources.getMessage("EMAIL.SET_COPYTO", new Object[0]), copyto);

            try {
                this.mimeMsg.setRecipients(RecipientType.CC, InternetAddress.parse(copyto));
                return true;
            } catch (Exception var3) {
                return false;
            }
        }
    }

    public boolean sendout() {
        try {
            this.mimeMsg.setContent(this.mp);
            this.mimeMsg.setSentDate(new Date());
            this.mimeMsg.saveChanges();
            this.logger.info(Resources.getMessage("EMAIL.SENDING", new Object[0]));
            Transport.send(this.mimeMsg);
            this.logger.info(Resources.getMessage("EMAIL.SEND_SUCC", new Object[0]));
            return true;
        } catch (Exception var2) {
            this.logger.error(Resources.getMessage("EMAIL.SEND_ERR", new Object[0]), var2);
            return false;
        }
    }

}
