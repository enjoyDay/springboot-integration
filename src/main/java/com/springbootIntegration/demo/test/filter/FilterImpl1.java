package com.springbootIntegration.demo.test.filter;

/**
 * @author liukun
 * @description
 * @date 2019/11/19
 */
public class FilterImpl1 implements Filter {
    @Override
    public void init() {

    }
    // 在该方法中，用接口接收，而不用实际类型接收，这样实现类不管是怎么变化，
    // 不管是不是改变，此处都不变，充分体现接口的妙处
    @Override
    public void doFilter(String request, String response, FilterChain chain) {
        System.out.println("过滤器1执行前");
        chain.doFilter(request,response);
        System.out.println("过滤器1执行后");
    }

    @Override
    public void destroy() {

    }
}
