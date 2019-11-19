package com.springbootIntegration.demo.test.filter;

/**
 * @author liukun
 * @description 模仿tomcat中的过滤器需要自己实现
 * @date 2019/11/19
 */
public interface Filter {
    void init();

    void doFilter(String request, String response, FilterChain chain);

    void destroy();
}
