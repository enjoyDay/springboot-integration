package com.springbootIntegration.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liukun
 * @description 文件工具类
 * @date 2019/9/14
 */
public class FileUtil {
    private static Logger logger = LogManager.getLogger();

    public FileUtil() {
    }

    /**
     * 读取文件，返回list列表
     * @param fileName 文件绝对路径
     * @return 返回list列表
     */
    public static List<String> readFile(String fileName) {
        List<String> list = new ArrayList();
        BufferedReader reader = null;
        FileInputStream fis = null;

        try {
            File f = new File(fileName);
            if (f.isFile() && f.exists()) {
                fis = new FileInputStream(f);
                reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

                String line;
                while((line = reader.readLine()) != null) {
                    if (!"".equals(line)) {
                        list.add(line);
                    }
                }
            }
        } catch (Exception var18) {
            logger.error("readFile", var18);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException var17) {
                logger.error("InputStream关闭异常", var17);
            }

            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException var16) {
                logger.error("FileInputStream关闭异常", var16);
            }

        }

        return list;
    }
}
