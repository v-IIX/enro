package edu.lxq.enro.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringBootController {

	@RequestMapping({ "/", "/index" })
	public String index(String username, String password) {
		return "index";
	}

	/*
	 * @RequestMapping("/login") public String login(String username, String
	 * password) { return "login"; }
	 */

	@RequestMapping("/login")
	public ModelAndView login(String username, String password) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", false);
		mav.addObject("code", 200);
		mav.setViewName("login");
		return mav;
	}

	/*
	 * @PostMapping("/login") public String login(String username, String password)
	 * { return "index"; }
	 */

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