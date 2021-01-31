package edu.lxq.enro.security;

import java.io.IOException;

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
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		if (loginType.equalsIgnoreCase("json")) {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(new Result(true,200,"success"));
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(json);
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
