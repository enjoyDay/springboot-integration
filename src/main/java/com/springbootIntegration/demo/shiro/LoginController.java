package com.springbootIntegration.demo.shiro;

import com.alibaba.fastjson.JSONObject;
import com.springbootIntegration.demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liukun
 * @description
 * @date 2019/9/15
 */
@RestController
public class LoginController {
    @PostMapping("/login")
    public String login(String username, String password, Model model){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject currentUser = SecurityUtils.getSubject();//获取subject

        try{
            //主体提交登录请求到securityManager
            currentUser.login(token);
        }catch (IncorrectCredentialsException ice){
            model.addAttribute("msg","密码不正确");
        }catch(UnknownAccountException uae){
            model.addAttribute("msg","账号不存在");
        }catch(AuthenticationException ae){
            model.addAttribute("msg","状态不正常");
        }
        if(currentUser.isAuthenticated()){
            System.out.println("认证成功");
            model.addAttribute("currentUser",currentUser());
            return "success";
        }
        else{
            token.clear();
            return "login";
        }
    }
    /**
     * shiro获取当前用户
     * @return
     */
    private User currentUser(){
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        return  currentUser;
    }

}
