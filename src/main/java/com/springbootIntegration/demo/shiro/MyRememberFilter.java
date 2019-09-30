package com.springbootIntegration.demo.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liukun
 * @description
 * @date 2019/9/15
 */
public class MyRememberFilter extends FormAuthenticationFilter {

    protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response, Object mappedValue){
        Subject subject=getSubject(request,response);
        if(!subject.isAuthenticated() && subject.isRemembered()){
            if(subject.getSession().getAttribute("user")==null &&subject.getPrincipal()!=null){
                subject.getSession().setAttribute("user",subject.getPrincipal());
            }

        }

        return subject.isAuthenticated() || subject.isRemembered();
    }
}
