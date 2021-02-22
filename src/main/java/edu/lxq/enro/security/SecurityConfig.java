package edu.lxq.enro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyFailureHandler myFailureHandler;
	@Autowired
	private MySuccessHandler mySuccessHandler;
	@Autowired
	private MyExpiredSessionStrategy myExpiredSessionStrategy;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private MyRBACService myRBACService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*
		 * auth.inMemoryAuthentication().withUser("admin").password(new
		 * BCryptPasswordEncoder().encode("123")).roles("admin")
		 * .and().withUser("user").password(new
		 * BCryptPasswordEncoder().encode("123")).roles("user").and()
		 * .passwordEncoder(new BCryptPasswordEncoder());
		 */

		auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/layui/**"); // 这个是用来忽略一些url地址，对其不进行校验，通常用在一些静态文件中。
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/index.html",
		// "/index").permitAll().anyRequest().authenticated();
		http.authorizeRequests().antMatchers("/index.html", "/index", "/login").permitAll().anyRequest()
				.access("@rbacService.hasPermission(request,authentication)");
		http.formLogin().loginPage("/login").loginProcessingUrl("/action").usernameParameter("username")
				.passwordParameter("password").successHandler(mySuccessHandler).failureHandler(myFailureHandler).permitAll();
		http.logout().logoutUrl("/logout").logoutSuccessHandler((req, res, authentication) -> {
			res.setContentType("application/json;charset=utf-8");
			PrintWriter out = res.getWriter();
			out.write("注销成功");
			out.flush();
			out.close();
		}).permitAll();
		/*
		 * http.exceptionHandling().authenticationEntryPoint((req, res, authException)
		 * -> { res.setContentType("application/json;charset=utf-8"); PrintWriter out =
		 * res.getWriter(); out.write("检测到未登录状态，请先登录"); out.flush(); out.close(); });
		 */
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login")
				.sessionFixation().migrateSession()

				/*
				 * .invalidSessionStrategy(new InvalidSessionStrategy() {
				 * 
				 * @Override public void onInvalidSessionDetected(HttpServletRequest request,
				 * HttpServletResponse response) throws IOException, ServletException {
				 * response.setContentType("application/json;charset=UTF-8");
				 * response.getWriter().append("session无效，请重新登录"); response.getWriter().flush();
				 * response.getWriter().close(); } })
				 */.maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login")
		/*
		 * .expiredSessionStrategy(new SessionInformationExpiredStrategy() {
		 * 
		 * @Override public void onExpiredSessionDetected(SessionInformationExpiredEvent
		 * event) throws IOException, ServletException { HttpServletResponse response =
		 * event.getResponse();
		 * response.setContentType("application/json;charset=UTF-8");
		 * response.getWriter().write("当前用户已在其他地方登录..."); response.getWriter().flush();
		 * response.getWriter().close(); } })
		 */;
	}
}
