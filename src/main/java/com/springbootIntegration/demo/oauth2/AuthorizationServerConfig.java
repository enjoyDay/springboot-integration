package com.springbootIntegration.demo.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author liukun
 * @description 认证授权中心，配置授权信息
 * oauth2使用
 *
 *
 * 授权模式
 * 使用授权码获取时的地址：http://localhost:8080/oauth/authorize?response_type=code&client_id=client_1&redirect_uri=http://localhost:8080/index.html
 * 中间会回调到的地址：http://localhost:8080/index.html?code=iG2VvN
 * 根据授权码获取access_token:http://localhost:8080/oauth/token?grant_type=authorization_code&code=qlHkCu&redirect_uri=http://localhost:8080/index.html&scope=all
 *
 * 客户端模式：
 * 获取access_token地址：http://localhost:8080/oauth/token?grant_type=client_credentials&scope=test&client_id=client_1&client_secret=123456
 * @date 2020/1/12
 */
@Configuration
@EnableAuthorizationServer // 开启认证授权中心
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    // accessToken有效期
    private int accessTokenValiditySeconds = 7200; // 两小时
    private int refreshTokenValiditySeconds = 7200;
    // 密码模式
    // 添加商户信息
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // withClient appid
//        clients.inMemory().withClient("client_1").secret(passwordEncoder().encode("123456"))
//                .authorizedGrantTypes("password","client_credentials","refresh_token").scopes("all").accessTokenValiditySeconds(accessTokenValiditySeconds);
//    }

    // 授权码模式,这个配置的是客户端申请的app_id和app_secrete
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // withClient appid
        clients.inMemory().withClient("client_1")
                .secret(passwordEncoder().encode("123456"))
                .authorizedGrantTypes("password", "client_credentials", "refresh_token", "authorization_code")
                .scopes("all").redirectUris("http://localhost:8080/index.html")
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
    }

    // 客户端模式
    // 这个格式要在 Spring Security 5.0开始
    // String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");

    // 客户端详情信息
    // 客户端详细信息在这里初始化，你能够把客户端详情信息写死在这或者是通过数据库
    // 来存储调取详情信息
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                // 用于标识用户ID
//                .withClient("client_1")
//                .authorizedGrantTypes("client_credentials", "refresh_token")//授权方式
//                // 授权范围
//                .scopes("test")
//                .authorities("oauth2")
//                // 客户端安全码,secret密码配置从 Spring Security 5.0开始必须以 {bcrypt}+加密后的密码 这种格式填写;
//                .secret(passwordEncoder().encode("123456"));
//    }

    /**
     *
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 必须加上他，不然刷新令牌接收会报错
        endpoints.authenticationManager(authenticationManager())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,
                        HttpMethod.POST);
        // 刷新令牌
        endpoints.authenticationManager(authenticationManager());
        endpoints.userDetailsService(userDetailsService());
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * @param oauthServer
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 允许表单认证
        oauthServer.allowFormAuthenticationForClients();
        // 允许check_token访问
        oauthServer.tokenKeyAccess("permitAll()");
        oauthServer.checkTokenAccess("isAuthenticated()");
    }

    @Bean
    AuthenticationManager authenticationManager() {
        AuthenticationManager authenticationManager = new AuthenticationManager() {

            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return daoAuhthenticationProvider().authenticate(authentication);
            }
        };
        return authenticationManager;
    }

    @Bean
    public AuthenticationProvider daoAuhthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    // 这个用于验证，验证用户输入的账号、密码与这个是否一致（也就是在第三方登录地方输入的账号密码要与这个一致才能通过）
    // 设置添加用户信息,正常应该从数据库中读取
    //todo 需要设置从数据库中获取数据，用于验证用户信息，包括账号，密码，权限
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456"))
                .authorities("ROLE_USER").build());
        userDetailsService.createUser(User.withUsername("user_2").password(passwordEncoder().encode("1234567"))
                .authorities("ROLE_USER").build());
        return userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 加密方式
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
}
