package com.springbootIntegration.demo.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springbootIntegration.demo.entity.User;
import com.springbootIntegration.demo.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liukun
 * @description 权限检查类
 * @date 2019/9/15
 */
//AuthorizingRealm 类继承自 AuthenticatingRealm, 但没有实现 AuthenticatingRealm
//中的 doGetAuthenticationInfo, 所以认证和授权只需要继承 AuthorizingRealm 就可以了.
// 同时实现他的两个抽象方法.
@Component
public class CustomRealm extends AuthorizingRealm implements Realm {

    @Resource
    UserMapper userMapper;

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从token获取用户名,从主体传过来的认证信息中获取
        //1 获取用户输入的账号
        String username = (String) authenticationToken.getPrincipal();
        //2 通过username去数据库中查找实体类
        QueryWrapper<User> condition = new QueryWrapper<User>().eq("name",username);
        User user = userMapper.selectOne(condition);
        if (user == null) {
            return null;
        }
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        //4.用户账号状态验证等其他业务操作
//        if(!user.getAvailable()){
//            throw new AuthenticationException("该账号已经被禁用");
//        }
        //5.返回身份处理对象
        return simpleAuthenticationInfo;
    }
    //授权限
    //角色权限和对应权限添加
    //Authorization授权，将数据库中的角色和权限授权给输入的用户名
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录的用户名
        User user = (User) principalCollection.getPrimaryPrincipal();
        //到数据库里查询要授权的内容

        //记录用户的所有角色和权限

        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();//权限信息

        //这是从数据库中获取用户的角色和权限
//        for(Role r:user.getRoles()){
//            //将所有的角色信息添加进来。
//            simpleAuthorizationInfo.addRole(r.getRname());
//            for(Permission p:r.getPermissions()){
//                //将此次遍历到的角色的所有权限拿到，添加·进来
//                simpleAuthorizationInfo.addStringPermission(p.getPname());
//            }
//        }
        return simpleAuthorizationInfo;
    }
}
