package edu.lxq.enro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootController {
	@RequestMapping("/hello")
	public String index() {
		return "hello springboot";
	}
}