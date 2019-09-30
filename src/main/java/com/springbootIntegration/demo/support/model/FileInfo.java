package com.springbootIntegration.demo.support.model;

import java.io.Serializable;

/**
 * @author liukun
 * @description
 * @date 2019/9/14
 */
public class FileInfo implements Serializable {
    private String orgName;
    private String fileType;
    private String fileName;
    private Long fileSize;

    public FileInfo() {
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
