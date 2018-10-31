package com.zdb.demoSecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zdb.demoSecurity.CustomUserService;
import com.zdb.demoSecurity.filter.JWTAuthenticationFilter;
import com.zdb.demoSecurity.filter.JwtLoginFilter;
import com.zdb.demoSecurity.handler.CustomAccessDeniedHandler;
import com.zdb.demoSecurity.handler.CustomAuthenticationSuccessHandler;
import com.zdb.demoSecurity.handler.CustomLogoutSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;


@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity 
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{//1
	
	/**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            // -- register url
            "/signup",
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/login.html",
            "/static/**",
            "/loginGet",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Bean
	UserDetailsService customUserService(){ //2
		return new CustomUserService(); 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(customUserService()); //3
		//注入userDetailsService的实现类  
		auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());  
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		// 由于使用的是JWT，我们这里不需要csrf
		.csrf().disable()
		//.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.authorizeRequests()
		.antMatchers("/static/**" , "/about","/signup","/loginGet").permitAll()  //无需访问权限,加上/**所有都不用过滤
		//.antMatchers("/css/**","/img/**","/**").permitAll()
		
		//.antMatchers( "/admin/**").hasRole("ADMIN" )
        //.antMatchers( "/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
						.anyRequest().authenticated() //4
						.and()
			            .addFilter(new JwtLoginFilter(authenticationManager()))//JWT相关
			            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
						
						.formLogin()
							.loginPage("/login")
							.failureUrl("/login?error")
							//.defaultSuccessUrl("/testjwt")
							.permitAll() //5
						.and()
						.logout().permitAll(); //6
	
/*
		http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler) // 自定义访问失败处理器
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .formLogin().loginPage("/login").failureUrl("/login?error").successHandler(customAuthenticationSuccessHandler).permitAll()
                .and()
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll();*/
	
	}
}
