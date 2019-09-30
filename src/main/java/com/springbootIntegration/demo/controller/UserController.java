package com.springbootIntegration.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.springbootIntegration.demo.entity.User;
import com.springbootIntegration.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liukun
 * @since 2019-09-15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/list")
    public JSONObject userList(){
        String s = "s";
        List<User> userList = userService.getUserList(s);
        JSONObject json = new JSONObject();
        json.put("list",userList);

        System.out.println("获得的结果："+json.toJSONString());
        return json;
    }

}
