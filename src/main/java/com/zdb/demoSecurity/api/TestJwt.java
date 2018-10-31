package com.zdb.demoSecurity.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testjwt")
public class TestJwt {
	@GetMapping("/greeting")
	public String greeting(){
		return "hello jwt";
	}

}
