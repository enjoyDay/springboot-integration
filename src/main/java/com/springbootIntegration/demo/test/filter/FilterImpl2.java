package com.springbootIntegration.demo.test.filter;

/**
 * @author liukun
 * @description
 * @date 2019/11/19
 */
public class FilterImpl2 implements Filter {
    @Override
    public void init() {

    }

    @Override
    public void doFilter(String request, String response, FilterChain chain) {
        System.out.println("过滤器2执行前");
        chain.doFilter(request,response);
        System.out.println("过滤器2执行后");
    }

    @Override
    public void destroy() {

    }
}
