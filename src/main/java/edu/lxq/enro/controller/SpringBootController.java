package edu.lxq.enro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class SpringBootController {

	@RequestMapping({ "/", "/index" })
	public String index(String username, String password) {
		return "index";
	}

	@PostMapping("/login")
	public String login(String username, String password) {
		return "index";
	}

	@RequestMapping("/user")
	public String user() {
		return "user";
	}

	@RequestMapping("/log")
	public String logger() {
		return "logger";
	}

	@RequestMapping("/service1")
	public String service1() {
		return "service1";
	}

	@RequestMapping("/service2")
	public String service2() {
		return "service2";
	}
}