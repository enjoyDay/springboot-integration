package com.springbootIntegration.demo.controller;

import com.springbootIntegration.demo.entity.User;
import com.springbootIntegration.demo.service.UserService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * UserController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/25/2019</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        //适用于不mock其他依赖接口
        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc =  MockMvcBuilders.standaloneSetup(userController).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: userList()
     */
    @Test
    public void testUserList() throws Exception {

        User user = new User();
        user.setName("刘坤");
        user.setAge(26);
        user.setEmail("1asd");
        user.setPassword("password");
        user.setId(10000L);
        List<User> list = new ArrayList<>();
        list.add(user);

        Mockito.when(userService.getUserList(Mockito.anyString())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


} 
