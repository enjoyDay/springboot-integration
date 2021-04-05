package com.springbootIntegration.demo.spider.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author liukun
 * @description
 * @since 2021/4/5
 */
public class LoadDocument {
    public static void main(String[] args) throws IOException {
        // 从字符串中加载文档
        String html = "<html><head><title>learn jsoup</title></head>"
                + "<body id='body'><p>Parse and traverse an HTML document.</p></body></html>";
        Document doc1 = Jsoup.parse(html);

        // 从URL中加载文档
        // 从URL直接加载 HTML 文档
        Document doc2 = Jsoup.connect("http://itmyhome.com/").get();
        String title = doc2.title();
        System.out.println(title);

        // 从文件中加载HTML文档
        File input = new File("D:/index.html");
        // 而第三个名为baseURL的参数的意思就是当HTML文档使用相对路径方式引用外部文件时，
        //jsoup会自动为这些URL加上一个前缀，也就是这个 baseURL。
        Document doc = Jsoup.parse(input, "UTF-8","http://itmyhome.com");


    }
}
