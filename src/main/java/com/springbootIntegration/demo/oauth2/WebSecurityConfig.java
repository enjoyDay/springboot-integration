package com.springbootIntegration.demo.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author liukun
 * <p>
 * 配置spring security
 * @description 使用授权模式时需要添加这个配置 添加Security权限
 * @date 2020/1/12
 */
// 开启权限验证
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

    // 拦截所有请求,使用httpBasic方式登陆
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/oauth/**").fullyAuthenticated().and().httpBasic();
    }

}

