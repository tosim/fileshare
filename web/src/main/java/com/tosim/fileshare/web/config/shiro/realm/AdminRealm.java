package com.tosim.fileshare.web.config.shiro.realm;

import com.tosim.fileshare.common.constants.Constants;
import com.tosim.fileshare.common.domain.Admin;
import com.tosim.fileshare.web.mapper.AdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    private RedisSessionDAO redisSessionDAO;
    @Autowired
    private AdminMapper adminMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("后台用户登录成功之后开始授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("admin");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("adminRealm  被调用");
        //获取用户的输入的账号.
        //log.debug("登录时候的认证～～～"+"username = " + String.valueOf(token.getPrincipal()) + ", pass = " + new String((char[])token.getCredentials()));
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        Admin admin = adminMapper.selectByLoginName(username);
        if (admin == null)
            throw new UnknownAccountException();
        SimpleAuthenticationInfo authenticationInfo;
        authenticationInfo = new SimpleAuthenticationInfo(
                admin, //用户
                admin.getPasswd(), //使用密码作为登录凭据
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );

        String encryptPassword = password;//不加密
        //String encryptPassword = PasswordHelper.encryptPassword(password, username);
        //如果登录成功
        if (encryptPassword.equals(admin.getPasswd())) {
            updateUserAndSetSession(admin);
        }
        return authenticationInfo;
    }

    //登录成功后清除前一个登录的信息，从而实现一个帐号只能在一个设备上登录
    protected void updateUserAndSetSession(Admin admin) {
        // 当验证都通过后，把用户信息放在session里,更新数据库的token
        Session session = SecurityUtils.getSubject().getSession();
        String newToken = session.getId().toString();
        adminMapper.updateTokenByLoginName(newToken, admin.getLoginName());
        //将用户信息存入session缓存
        session.setAttribute(Constants.USER_ID,admin.getAdminId());
        session.setAttribute(Constants.USER_SESSION, admin);
    }
}
