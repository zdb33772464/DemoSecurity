package com.zdb.demoSecurity.support;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

//启动不实例化BaseRepository，自定义Repository
@NoRepositoryBean
public interface CustomRepository <T,ID extends Serializable> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T>{
	
	Page<T> findByAuto(T example,Pageable pageable);

	/**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);
	
	

}
