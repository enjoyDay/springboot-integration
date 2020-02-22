package com.springbootIntegration.demo.test.dubbo;

/**
 * @author liukun
 * @description 服务接口，调用方和被调用方都要此接口，可制作成公共包
 * @date 2020/2/22
 */
public interface UserService {
    //使用userid查询 用户信息
    public String getUser(Long userId);

}
