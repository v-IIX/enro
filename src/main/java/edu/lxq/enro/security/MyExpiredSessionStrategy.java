package edu.lxq.enro.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {
	private ObjectMapper objectMapper = new ObjectMapper();
//private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		Map<String, Object> map = new HashMap<>(16);
		map.put("code", 0);
		map.put("msg", "已经另一台机器登录，您被迫下线..." + event.getSessionInformation().getLastRequest());
		// Map -> Json
		String json = objectMapper.writeValueAsString(map);

		event.getResponse().setContentType("application/json;charset=UTF-8");
		event.getResponse().getWriter().write(json);

		// 如果是跳转html页面，url代表跳转的地址
		// redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(),
		// "url");
	}
}