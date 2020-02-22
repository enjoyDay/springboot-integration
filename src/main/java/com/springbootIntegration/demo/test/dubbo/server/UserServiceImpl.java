package com.springbootIntegration.demo.test.dubbo.server;

import com.springbootIntegration.demo.test.dubbo.UserService;

/**
 * @author liukun
 * @description
 * @date 2020/2/22
 */
public class UserServiceImpl implements UserService {
    @Override
    public String getUser(Long userId) {
        System.out.println("###会员服务接受参数开始userId:" + userId);
        if (userId == 1) {
            return "接收userId参数是" + userId + "六六";
        }
        if (userId == 2) {
            return "接收userId参数是" + userId + "张杰";
        }
        System.out.println("###会员服务接受参数结束###");
        return "未找到用户...";

    }
}
