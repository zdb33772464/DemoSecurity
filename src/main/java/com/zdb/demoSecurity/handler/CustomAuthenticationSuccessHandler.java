package com.zdb.demoSecurity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zdb.demoSecurity.domain.SysUser;
import com.zdb.demoSecurity.util.CookieUtil;
import com.zdb.demoSecurity.util.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: zhaoxinguo
 * @Date: 2018/8/20 14:47
 * @Description: 自定义认证成功处理器
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LOGGER.info("====================认证成功====================");
        System.out.println("====================认证成功====================");
        SysUser user = new SysUser();
		user.setUsername(authentication.getName());
		CookieUtil.addCookie(httpServletResponse, "name", user.getUsername(), 0);
//		String url=request.getHeader("Referer").substring(request.getHeader("Referer").indexOf('=')+2);
//		LogUtil.error(getClass(), url);
		httpServletResponse.sendRedirect("/home");
		new JwtUtils().generateToken(httpServletResponse, user);
    }
}
