package edu.lxq.enro.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Value("${spring.security.loginType}")
	private String loginType;

	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
		if (loginType.equalsIgnoreCase("json")) {
			ObjectMapper objectMapper = new ObjectMapper();
			res.setContentType("application/json;charset=UTF-8");
			Result result = new Result(false, 400, "failure", exception);
			PrintWriter out = res.getWriter();
			out.write(objectMapper.writeValueAsString(result));
			out.flush();
			out.close();
		} else {
			super.onAuthenticationFailure(req, res, exception);
		}
	}

}
