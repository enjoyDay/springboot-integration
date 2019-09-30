package com.springbootIntegration.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springbootIntegration.demo.entity.User;
import com.springbootIntegration.demo.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springbootIntegration.demo.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liukun
 * @since 2019-09-15
 */
@Service
public class UserService{
    @Resource
    private UserMapper userMapper;

    public List<User> getUserList(String s){
        System.out.println("参数："+s);
        String time = DateUtil.getDate();
        System.out.println("时间1："+time);
        System.out.println("时间2："+DateUtil.getDateTime());
        return userMapper.selectList(null);
    }
}
