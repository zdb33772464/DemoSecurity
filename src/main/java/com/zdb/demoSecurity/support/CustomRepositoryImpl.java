package com.zdb.demoSecurity.support;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.zdb.demoSecurity.util.JwtTokenUtil;

import static com.zdb.demoSecurity.specs.CustomerSpecs.*;

public class CustomRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements CustomRepository<T, ID> {

	private final EntityManager entityManager;
	private JwtTokenUtil jwtTokenUtil;
	public CustomRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<T> findByAuto(T example, Pageable pageable) {
		// TODO Auto-generated method stub
		//调用CustomerSpecs的byAuto
		return findAll(byAuto(entityManager,example),pageable);
	}

	
	@Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }

}
