package com.zdb.demoSecurity.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdb.demoSecurity.domain.SysUser;
import com.zdb.demoSecurity.exception.UsernameIsExitedException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhaoxinguo on 2018/06/05.
 */
@RestController
@RequestMapping("/users")
@Api(value = "注册管理", description = "注册管理")
public class RegisterController extends BaseController {

    /**
     * 注册用户 默认开启白名单
     * @param user
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("/signup")
    public SysUser signup(@RequestBody SysUser user) {
    	SysUser bizUser = userRepository.findByUsername(user.getUsername());
        if(null != bizUser){
            throw new UsernameIsExitedException("用户已经存在");
        }
        /*user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));*/
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
