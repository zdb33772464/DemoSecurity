package com.zdb.demoSecurity.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.zdb.demoSecurity.constant.ConstantKey;
import com.zdb.demoSecurity.domain.Msg;
import com.zdb.demoSecurity.domain.SysUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
//@RestController
public class HomeController {
	
	@RequestMapping("/")
	public String index(Model model,HttpServletRequest request,
            HttpServletResponse response){
		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "redirect:/home";//跳转到/home
	}
	
	@RequestMapping("/home")
	public String home(Model model,HttpServletRequest request,
            HttpServletResponse response) {
		
		Msg msg = new Msg("HOME","login home","welcome");
		model.addAttribute("msg",msg);
		return "home";
	}
	
	
	@RequestMapping("/testjwt")
	public String TestJwt(){		
		return "hello jwt";
	}
	


	    
	


}
