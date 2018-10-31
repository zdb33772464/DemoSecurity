package com.zdb.demoSecurity;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.zdb.demoSecurity.dao.SysUserRepository;
import com.zdb.demoSecurity.domain.SysUser;
import com.zdb.demoSecurity.util.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSecurityApplicationTests {
	
	@Autowired
	SysUserRepository userRepository;
	
	@Autowired
	RedisUtil redisUtil;
	@Test
	public void contextLoads() {
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
		redisUtil.set("mzname", "zdb", 60);
		/*List list = userRepository.findAll();
		for(int i=0;i<list.size();i++) {
			SysUser user = (SysUser)list.get(i);
			System.out.println("name:"+user.getUsername());
		}*/
		
	}

}
