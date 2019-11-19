package com.springbootIntegration.demo.test.filter;

/**
 * @author liukun
 * @description
 * @date 2019/11/19
 */
public class Test {
    public static void main(String[] args) {
        FilterImpl1 filterImpl1 = new FilterImpl1();
        FilterImpl2 filterImpl2 = new FilterImpl2();
        ApplicationFilterChain applicationFilterChain = new ApplicationFilterChain();
        applicationFilterChain.append(filterImpl1);
        applicationFilterChain.append(filterImpl2);

        applicationFilterChain.doFilter("request", "response");
    }
}
