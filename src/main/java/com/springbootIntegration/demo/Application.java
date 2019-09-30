package com.springbootIntegration.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liukun
 * @description jar启动类
 * @date 2019/9/1
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
@MapperScan("com.springbootIntegration.demo.mapper")
public class Application extends SpringBootServletInitializer {
    @Profile(value = {"war"})
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    /**
     * main函数
     * @param args 控制台输入参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("启动完成");
    }
}




