package com.zdb.demoSecurity.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.zdb.demoSecurity.domain.Msg;
import com.zdb.demoSecurity.domain.SysUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;

import com.zdb.demoSecurity.constant.ConstantKey;
import com.zdb.demoSecurity.dao.SysUserRepository;

@RestController
public class DataController {

	@Autowired
	SysUserRepository sysUserRepository;
	@RequestMapping("/auto")
	public Page<SysUser> auto(SysUser sysUser){
		Page<SysUser> pageUser = sysUserRepository.findByAuto(sysUser,new PageRequest(0,10));
		return pageUser;
		
	}
	
	/**
     * 保存
     * save支持批量保存：<S extends T> Iterable<S> save(Iterable<S> entities);
     * <p>
     * 删除：
     * 删除支持使用id，对象以，批量删除及删除全部：
     * void delete(ID id);
     * void delete(T entity);
     * void delete(Iterable<? extends T> entities);
     * void deleteAll();
     */
	@RequestMapping("/save")
    public SysUser save(String username, String password) {
		SysUser user = new SysUser();
		user.setUsername(username);
		user.setPassword(password);
		SysUser p = sysUserRepository.save(user);
		

        return p;

    }


    /**
     * 测试findByAddress
     */
    @RequestMapping("/q1")
    public SysUser q1(String username) {

    	SysUser people =  sysUserRepository.findByUsername(username);

        return people;

    }

    /**
     * 测试findByNameAndAddress
     */
    @RequestMapping("/q2")
    public SysUser q2(String username, String password) {

        SysUser people = sysUserRepository.findByUsernameAndPassword(username, password);

        return people;

    }

    /**
     * 测试withNameAndAddressQuery
     */
    @RequestMapping("/q3")
    public SysUser q3(String username, String password) {

        SysUser p = sysUserRepository.withUsernameAndPasswordQuery(username, password);

        return p;

    }


    /**
     * 测试排序
     */
    @RequestMapping("/sort")
    public List<SysUser> sort() {

        List<SysUser> sysUser = sysUserRepository.findAll(new Sort(Direction.ASC, "username"));

        return sysUser;

    }

    /**
     * 测试分页
     */
    @RequestMapping("/page")
    public Page<SysUser> page() {

        Page<SysUser> pageUser = sysUserRepository.findAll(new PageRequest(1, 2));

        return pageUser;

    }
    
    /**
     * 刷新密钥
     *
     * @param authorization 原密钥
     * @return 新密钥
     * @throws AuthenticationException 错误信息
     */
    @GetMapping(value = "/refreshToken")
    public String refreshToken(@RequestHeader String authorization) throws AuthenticationException {
        return sysUserRepository.refreshToken(authorization);
    }
    
    @ApiOperation(value = "自定义登录")
    @RequestMapping(value = "/loginGet", method = {RequestMethod.POST})
    public String login(String username, String password, HttpServletResponse response) {
        SysUser userVo = sysUserRepository.findByUsernameAndPassword(username, password);
        if (userVo != null) {
            //**
            //自定义生成Token，因为使用了自定义登录，就不会执行JWTLoginFilter了，所以需要字段调用生成token并返给前端
             //*
            // 这里可以根据用户信息查询对应的角色信息，这里为了简单，我直接设置个空list
            List roleList = userVo.getRoles();
            String subject = userVo.getUsername() + "-" + roleList;
                    String token = Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000)) // 设置过期时间 365 * 24 * 60 * 60秒(这里为了方便测试，所以设置了1年的过期时间，实际项目需要根据自己的情况修改)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
    		return "home";
        }else {
        	return "login";//跳转到/home
        }
    }
 
    
}
