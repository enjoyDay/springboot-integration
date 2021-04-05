package com.springbootIntegration.demo.spider.navtiveJava;

import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author liukun
 * @description 使用Java的Http协议获取豆瓣信息
 * @since 2021/4/5
 */
public class UseNativeJava {

    public static void main(String[] args) {
        int start;//每页多少条
        int total = 0;//记录数
        int end = 40;//总共100条数据
        for (start = 0; start <= end; start += 20) {
            try {
                String address = "https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=" + start;
                JSONObject dayLine = new GetJson().getHttpJson(address, 1);
                System.out.println("start:" + start);
                JSONArray data = dayLine.getJSONArray("data");
                List<Movie> movies = JSON.parseArray(data.toString(), Movie.class);
                if (start >= end) {
                    System.out.println("已经爬取到底了");
                }

                for (Movie movie : movies) {
                    System.out.println(movie);
                }

                total += movies.size();
                System.out.println("正在爬取中---共爬取：" + total + "条数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
