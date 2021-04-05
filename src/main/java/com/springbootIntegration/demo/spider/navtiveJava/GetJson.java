package com.springbootIntegration.demo.spider.navtiveJava;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author liukun
 * @description 拟建立HTTP请求
 * @since 2021/4/5
 */
public class GetJson {
    public JSONObject getHttpJson(String url, int comeFrom) {
        try {
            // 模拟建立HTTP请求
            URL readUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) readUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立连接
            connection.connect();
            // 请求成功
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                // 10MB缓存
                byte[] buffer = new byte[10 * 1024 * 1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }

                String jsonString = byteArrayOutputStream.toString();
                byteArrayOutputStream.close();
                inputStream.close();

                // 转换成json数据处理
                JSONObject jsonArray = getJsonString(jsonString, comeFrom);
                return jsonArray;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JSONObject getJsonString(String jsonString, int comeFrom) {
        JSONObject jsonObject = null;
        if (comeFrom == 1) {
            return new JSONObject(jsonString);
        } else if (comeFrom == 2) {
            int indexStart = 0;
            // 字符处理
            for (int i = 0; i < jsonString.length(); i++) {
                if (jsonString.charAt(i) == '(') {
                    indexStart = i;
                    break;
                }
            }

            String strNew = "";
            // 分割字符串
            for (int i = indexStart; i < jsonString.length() - 1; i++) {
                strNew += jsonString.charAt(i);
            }

            return new JSONObject(strNew);
        }

        return jsonObject;

    }
}
