package com.springbootIntegration.demo.test.filter;

/**
 * @author liukun
 * @description 模仿tomcat中的过滤器链接口
 * @date 2019/11/19
 */
public interface FilterChain {
    void doFilter(String request, String response);
}
