package com.springbootIntegration.demo.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author liukun
 * @description 使用授权模式时需要添加这个配置 添加Security权限
 * spring security使用
 * @date 2020/1/12
 */
@Component
// 开启spring-security权限验证
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 认证成功后要处理的事
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;
    // 认证失败要处理的事
    @Autowired
    private MyAuthenticationFailureHandler failHandler;


    //用户认证信息（添加认证资源）
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加用户账号信息和权限
        auth.inMemoryAuthentication().
                // 用户
                withUser("admin").
                // 密码
                password("123456").
                // 这个是权限
                authorities("addOrder");
        // 如果想实现动态账号与数据库关联 在该地方改为查询数据库
        // 访问数据库可参考有道中的文档
    }

    // 配置HttpSecurity 拦截请求资源（因为它也是根据过滤器实现的）,表单登录
    protected void configure(HttpSecurity http) throws Exception {
        // 拦截请求, 权限名称
        http.authorizeRequests()
                // 下面这个也要做成动态与数据库连接的
                // 如何权限控制 给每一个请求路径 分配一个权限名称 然后账号只要关联该名称，就可以有访问权限
                .antMatchers("/showOrder").hasAnyAuthority("showOrder") // showOrder权限名
                .antMatchers("/addOrder").hasAnyAuthority("addOrder")
                .antMatchers("/login").permitAll()
                .antMatchers("/updateOrder").hasAnyAuthority("updateOrder")
                .antMatchers("/deleteOrder").hasAnyAuthority("deleteOrder")
                //并且关闭csrf
                // 拦截所有请求
                .antMatchers("/**").fullyAuthenticated().and().
                // 配置login界面，注意在这配置界面后，还要写一个controller层转发
                formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failHandler).and().
                // 禁止跨站，如果不禁掉，表单还要传token
                csrf().disable();

    }

//    // 拦截所有请求,使用httpBasic方式登陆，不常用
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests().
//                antMatchers("/oauth/**").
//                fullyAuthenticated().and().httpBasic();
//    }

    /**
     * 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
     * 注入AuthenticationManager接口，启用OAuth2密码模式
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

