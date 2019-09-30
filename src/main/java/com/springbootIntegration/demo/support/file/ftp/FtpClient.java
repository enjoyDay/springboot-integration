package com.springbootIntegration.demo.support.file.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;

import com.springbootIntegration.demo.support.exception.FtpException;
import com.springbootIntegration.demo.support.exception.FtpException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FtpClient {
    private Logger logger = LogManager.getLogger();
    private static final byte[] LOCK = new byte[]{0};
    private static FTPClient ftpClient = null;
    private Properties properties = null;
    private static final String FILELOCK = "Token.lock";

    public static void main(String[] args) throws Exception {
        String host = "10.116.1.65";
        int port = 21;
        String username = "ftpUser";
        String password = "123456";
        String localUpPath = "C:/bankData/Send/";
        String localDnPath = "C:/bankData/Feedback";
        String remotePath = "Feedback";
        FtpClient ftpClient = new FtpClient(host, port, username, password);
        ftpClient.uploadFile("send", localUpPath);
        ftpClient.downLoadFile(remotePath, localDnPath);
        ftpClient.close();
    }

    public FtpClient() {
    }

    public FtpClient(String host, int port, String username, String password) throws FtpException {
        this.init(host, port, username, password);
    }

    public void open() {
        this.init(this.properties.getProperty("FTPHOSTNAME"), Integer.valueOf(this.properties.getProperty("FTPPORT")), this.properties.getProperty("FTPUSERNAME"), this.properties.getProperty("FTPPASSWORD"));
    }

    private void init(String host, int port, String username, String password) throws FtpException {
        byte[] var5 = LOCK;
        synchronized(LOCK) {
            if (ftpClient == null) {
                ftpClient = new FTPClient();
            }

            try {
                ftpClient.connect(host, port);
            } catch (Exception var10) {
                throw new FtpException("FTP[" + host + ":" + port + "]连接失败!", var10);
            }

            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                try {
                    ftpClient.login(username, password);
                } catch (Exception var9) {
                    throw new FtpException("FTP用户[" + username + "]登陆失败!", var9);
                }

                this.logger.info("用户[" + username + "]登陆[" + host + "]成功.");
                this.properties.setProperty("userName", username);
                this.properties.setProperty("hostName", host);

                try {
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileTransferMode(10);
                    ftpClient.setFileType(2);
                } catch (Exception var8) {
                    this.logger.error("", var8);
                    throw new FtpException("FTP初始化出错!", var8);
                }

            } else {
                throw new FtpException("FTP连接出错!");
            }
        }
    }

    public void close() throws FtpException {
        byte[] var1 = LOCK;
        synchronized(LOCK) {
            try {
                ftpClient.logout();
            } catch (IOException var4) {
                this.logger.error("", var4);
                ftpClient = null;
                throw new FtpException("FTP退出登录出错!", var4);
            }

            this.logger.info("用户[" + this.properties.getProperty("userName") + "]退出登录[" + this.properties.getProperty("hostName") + "].");
        }
    }

    public boolean uploadFile(String remotePath, String localPath) throws FtpException {
        byte[] var3 = LOCK;
        synchronized(LOCK) {
            File file = new File(localPath);
            File[] files = file.listFiles();

            for(int i = 0; i < files.length; ++i) {
                if (!this.uploadFiles(files[i], remotePath)) {
                    return false;
                }
            }

            return files.length > 0;
        }
    }

    public boolean uploadFiles(File localeFile, String remotePath) throws FtpException {
        byte[] var3 = LOCK;
        synchronized(LOCK) {
            FileInputStream fis = null;

            boolean flag;
            try {
                if (localeFile.isDirectory()) {
                    flag = false;
                    ftpClient.makeDirectory(localeFile.getName());
                    ftpClient.changeWorkingDirectory(localeFile.getName());
                    this.logger.info("[" + localeFile.getAbsolutePath() + "]目录");
                    File[] files = localeFile.listFiles();
                    File[] var7 = files;
                    int var8 = files.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        File file = var7[var9];
                        if (!this.uploadFiles(file, remotePath + "/" + localeFile.getName())) {
                            boolean var11 = false;
                            return var11;
                        }

                        flag = true;
                    }

                    ftpClient.changeToParentDirectory();
                    boolean var28 = flag;
                    return var28;
                }

                if (!"Token.lock".equals(localeFile.getName())) {
                    fis = new FileInputStream(localeFile);
                    ftpClient.storeFile(localeFile.getName(), fis);
                    this.logger.info("[" + localeFile.getAbsolutePath() + "]上传成功!");
                    flag = true;
                    return flag;
                }

                flag = true;
            } catch (IOException var25) {
                this.logger.error("", var25);
                throw new FtpException("FTP上传[" + localeFile.getAbsolutePath() + "]出错!", var25);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException var24) {
                        ;
                    }
                }

            }

            return flag;
        }
    }

    public boolean downLoadFile(String remotePath, String localPath) throws FtpException {
        byte[] var3 = LOCK;
        synchronized(LOCK) {
            try {
                if (!ftpClient.changeWorkingDirectory(remotePath)) {
                    return false;
                } else {
                    FTPFile[] files = ftpClient.listFiles();
                    if (files.length > 0) {
                        File localdir = new File(localPath);
                        if (!localdir.exists()) {
                            localdir.mkdir();
                        }
                    }

                    FTPFile[] var12 = files;
                    int var6 = files.length;

                    boolean var10000;
                    for(int var7 = 0; var7 < var6; ++var7) {
                        FTPFile ff = var12[var7];
                        if (!this.downLoadFile(ff, localPath)) {
                            var10000 = false;
                            return var10000;
                        }
                    }

                    var10000 = files.length > 0;
                    return var10000;
                }
            } catch (IOException var10) {
                this.logger.error("", var10);
                throw new FtpException("FTP下载[" + localPath + "]出错!", var10);
            }
        }
    }

    public boolean downLoadFile(FTPFile ftpFile, String localPath) {
        String fileLocalPath = localPath + "/" + ftpFile.getName();
        File localFile;
        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) {
                FileOutputStream outputStream = null;

                boolean var6;
                try {
                    localFile = new File(fileLocalPath);
                    if (!localFile.getParentFile().exists()) {
                        localFile.getParentFile().mkdirs();
                    }

                    outputStream = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ftpFile.getName(), outputStream);
                    outputStream.flush();
                    outputStream.close();
                    this.logger.info("[" + localFile.getAbsolutePath() + "]下载成功!");
                    var6 = true;
                } catch (Exception var16) {
                    this.logger.error("", var16);
                    throw new FtpException("FTP下载[" + fileLocalPath + "]出错!", var16);
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException var15) {
                        ;
                    }

                }

                return var6;
            }
        } else {
            File file = new File(fileLocalPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            try {
                if (ftpClient.changeWorkingDirectory(ftpFile.getName())) {
                    this.logger.info("[" + file.getAbsolutePath() + "]目录");
                    localFile = null;
                    FTPFile[] files = ftpClient.listFiles();
                    FTPFile[] var21 = files;
                    int var7 = files.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        FTPFile file2 = var21[var8];
                        this.downLoadFile(file2, fileLocalPath);
                    }

                    ftpClient.changeToParentDirectory();
                    return true;
                }
            } catch (Exception var18) {
                this.logger.error("", var18);
                throw new FtpException("FTP下载[" + fileLocalPath + "]出错!", var18);
            }
        }

        return false;
    }

    public String getMaxFileName(String remotePath) {
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            FTPFile[] files = ftpClient.listFiles();
            Arrays.sort(files, new Comparator<FTPFile>() {
                public int compare(FTPFile o1, FTPFile o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
            return files[0].getName();
        } catch (IOException var3) {
            this.logger.error("", var3);
            throw new FtpException("FTP访问目录[" + remotePath + "]出错!", var3);
        }
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = new Properties();
        String[] key = new String[]{"FTPHOSTNAME", "FTPPORT", "FTPUSERNAME", "FTPPASSWORD"};
        String[] var3 = key;
        int var4 = key.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String element = var3[var5];
            this.properties.put(element, properties.get(element));
        }

    }
}