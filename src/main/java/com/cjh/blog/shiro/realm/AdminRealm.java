package com.cjh.blog.shiro.realm;

import com.cjh.blog.entity.User;
import com.cjh.blog.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class AdminRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 为当前登录用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权");
        return null;
    }
    /**
     * 验证当前登录用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("-------------执行验证-------------");
        //1.获取用户名
        String userName = (String) token.getPrincipal();
        //2.调用根据service层的方法
        User user = userService.selectUser(userName);
        if(user!=null) {
            //3.验证身份
            AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),"xxx");
            return authenticationInfo;
        }
        //验证失败
        return null;
    }
}
