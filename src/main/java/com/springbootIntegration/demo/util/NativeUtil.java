package com.springbootIntegration.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liukun
 * @description 本地工具类
 * @date 2019/9/14
 */
public final class NativeUtil {
    private static Logger logger = LogManager.getLogger();

    private NativeUtil() {
    }

    //获取本地主机名
    public static final String getHostName() {
        String hostName = "";

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var2) {
            logger.error(var2.getMessage(), var2);
        }

        return hostName;
    }
    //获取DUID
    public static final String getDUID() {
        String address = "";
        String command = "cmd.exe /c ipconfig /all";

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while((line = br.readLine()) != null) {
                if (line.indexOf("DUID") > 0) {
                    int index = line.indexOf(":");
                    index += 2;
                    address = line.substring(index);
                    break;
                }
            }

            br.close();
        } catch (IOException var6) {
            ;
        }

        return address;
    }
}
