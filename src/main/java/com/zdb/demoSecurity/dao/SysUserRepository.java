package com.zdb.demoSecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zdb.demoSecurity.domain.SysUser;
import com.zdb.demoSecurity.support.CustomRepository;;

/*public interface SysUserRepository extends JpaRepository<SysUser,Long>{

		SysUser findByUsername(String username);
		
		SysUser findByUsernameAndPassword(String username,String password);
}
*/
public interface SysUserRepository extends CustomRepository<SysUser,Long>{

	SysUser findByUsername(String username);
	
	SysUser findByUsernameAndPassword(String username,String password);
	
	@Query("select p from SysUser p where p.username=:username and p.password=:password")
	SysUser withUsernameAndPasswordQuery(@Param("username")String username,@Param("password")String password);
	
	
}
