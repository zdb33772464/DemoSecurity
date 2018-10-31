package com.zdb.demoSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import com.zdb.demoSecurity.dao.SysUserRepository;
import com.zdb.demoSecurity.domain.SysUser;

public class CustomUserService implements UserDetailsService{

	@Autowired
	SysUserRepository userRepository;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println("userName:"+username);
		SysUser user = userRepository.findByUsername(username);
		System.out.println("security登录校验方法");
		//System.out.print("user:"+user.getUsername());
		if(user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		return user;
	}
	

}
