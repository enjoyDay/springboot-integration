package com.springbootIntegration.demo.service;

import com.springbootIntegration.demo.util.DateUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * UserService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/29/2019</pre>
 */
@RunWith(PowerMockRunner.class)//使用这个不能注入，只能new使用的类
@SpringBootTest()
//@RunWith(SpringRunner.class)//使用这个类可以进行注入类
@PrepareForTest({DateUtil.class})
public class UserServiceTest {
    @Resource
    UserService userService;

    @Before
    public void before() throws Exception {
        userService = new UserService();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUserList(String s)
     */
    @Test
    public void testGetUserList() throws Exception {
        //将静态类mock掉
        PowerMockito.mockStatic(DateUtil.class);
        //对静态类的其他方法不mock，还是执行真实方法，需要执行下面这句话
        PowerMockito.spy(DateUtil.class);
        //mock静态类中指定的方法
        PowerMockito.when(DateUtil.getDateTime()).thenReturn("该方法被mock掉了");
        userService.getUserList("测试成功");
    }


} 
