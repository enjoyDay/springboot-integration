package com.springbootIntegration.demo.test.filter;

import java.util.LinkedList;

/**
 * @author liukun
 * @description 模仿tomcat中的过滤器实现，主要类
 * @date 2019/11/19
 */
public class ApplicationFilterChain implements FilterChain {

    // 数据集合，用来保存过滤器
    private LinkedList<Filter> linkedList = new LinkedList<>();

    public void append(Filter filter) {
        linkedList.add(filter);
    }

    private int pos = 0;
    private boolean executeTarget = false;

    /**
     * 该方法是实现重点
     * @param request
     * @param response
     */
    @Override
    public void doFilter(String request, String response) {
        if (pos < linkedList.size()) {
            Filter filter = linkedList.get(pos++);
            //将自己传进去，递归调用本方法
            filter.doFilter(request, response, this);
        }
        if (pos == linkedList.size()) {
            if (executeTarget == false){
                executeTarget = true;
                System.out.println("执行目标方法");
            }
        }
    }
}
