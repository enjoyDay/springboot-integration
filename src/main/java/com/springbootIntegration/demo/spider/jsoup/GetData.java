package com.springbootIntegration.demo.spider.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author liukun
 * @description 数据抽取
 * @since 2021/4/5
 */
public class GetData {
    public static void main(String[] args) throws IOException {
        String html = "<html><head><title>learn jsoup</title></head>"
                + "<body id='content'><a href='itmyhome.com'>hello</a>"
                + "<a href='blog.itmyhome.com'>jsoup</a></body></html>";

        Document doc = Jsoup.parse(html);
        // 根据ID来获取元素
        Element content = doc.getElementById("content");
        // 根据标签获取元素
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            // 获取元素中的指定属性
            String linkHref = link.attr("href");
            // 获取元素中的文本
            String linkText = link.text();
            System.out.println("href属性："+ linkHref + ", 文本内容：" + linkText);
        }



        Document doc3 = Jsoup.connect("http://jsoup.org").get();

        Element link = doc3.select("a").first();
        System.out.println(link);
        String relHref = link.attr("href"); // == "/"
        System.out.println(relHref);
        String attr = link.attr("abs:href");
        System.out.println(attr);
    }
}
