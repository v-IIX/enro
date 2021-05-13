package edu.lxq.enro.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Value("${spring.security.loginType}")
	private String loginType;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws ServletException, IOException {
		if (loginType.equalsIgnoreCase("json")) {
			Object pricipal = auth.getPrincipal();
			res.setContentType("application/json;charset=UTF-8");
			ObjectMapper objectMapper = new ObjectMapper();
			Result result = new Result(true, 200, "success", pricipal);
			PrintWriter out = res.getWriter();
			out.write(objectMapper.writeValueAsString(result));
			out.flush();
			out.close();
		} else {
			// 实现跳转
			// super.setDefaultTargetUrl("/index");
			super.onAuthenticationSuccess(req, res, auth);
		}
	}

}
