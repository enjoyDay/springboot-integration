package com.springbootIntegration.demo.support.email;

import java.io.Serializable;

/**
 * @author liukun
 * @description 发送邮箱的bean
 * @date 2019/9/14
 */
public class Email implements Serializable {
    private String host;
    private String port;
    private boolean isSSL;
    private String userkey;
    private String from;
    private String name;
    private String password;
    private String sendTo;
    private String copyTo;
    private String topic;
    private String body;
    private String[] fileAffix;

    public Email() {
        this.isSSL = false;
    }

    public Email(String sendTo, String topic, String body) {
        this((String)null, sendTo, (String)null, topic, body, (String[])null);
    }

    public Email(String sendTo, String topic, String body, String[] fileAffix) {
        this(sendTo, (String)null, topic, body, (String[])fileAffix);
    }

    public Email(String sendTo, String copyTo, String topic, String body) {
        this((String)null, sendTo, copyTo, topic, body, (String[])null);
    }

    public Email(String sendTo, String copyTo, String topic, String body, String[] fileAffix) {
        this((String)null, sendTo, copyTo, topic, body, fileAffix);
    }

    public Email(String from, String sendTo, String copyTo, String topic, String body) {
        this(from, sendTo, copyTo, topic, body, (String[])null);
    }

    public Email(String from, String sendTo, String copyTo, String topic, String body, String[] fileAffix) {
        this(from, (String)null, (String)null, (String)null, sendTo, copyTo, topic, body, (String[])fileAffix);
    }

    public Email(String from, String name, String password, String key, String sendTo, String copyTo, String topic, String body) {
        this((String)null, from, name, password, key, sendTo, copyTo, topic, body, (String[])null);
    }

    public Email(String from, String name, String password, String key, String sendTo, String copyTo, String topic, String body, String[] fileAffix) {
        this((String)null, from, name, password, key, sendTo, copyTo, topic, body, fileAffix);
    }

    public Email(String host, String from, String name, String password, String key, String sendTo, String copyTo, String topic, String body) {
        this(host, from, name, password, key, sendTo, copyTo, topic, body, (String[])null);
    }

    public Email(String host, String from, String name, String password, String key, String sendTo, String copyTo, String topic, String body, String[] fileAffix) {
        this.isSSL = false;
        this.host = host;
        this.from = from;
        this.name = name;
        this.password = password;
        this.userkey = key;
        this.sendTo = sendTo;
        this.copyTo = copyTo;
        this.topic = topic;
        this.body = body;
        this.fileAffix = fileAffix;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserkey() {
        return this.userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public boolean isSSL() {
        return this.isSSL;
    }

    public void setSSL(boolean isSSL) {
        this.isSSL = isSSL;
    }

    public String getUserKey() {
        return this.userkey;
    }

    public void setUserKey(String key) {
        this.userkey = key;
    }

    public String getSendTo() {
        return this.sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getCopyTo() {
        return this.copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] getFileAffix() {
        return this.fileAffix;
    }

    public void setFileAffix(String[] fileAffix) {
        this.fileAffix = fileAffix;
    }
}
