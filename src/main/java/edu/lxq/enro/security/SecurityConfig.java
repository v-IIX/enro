package edu.lxq.enro.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyFailureHandler myFailureHandler;
	@Autowired
	private MySuccessHandler mySuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
				.password(new BCryptPasswordEncoder().encode("123")).roles("admin");

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/layui/**"); // 这个是用来忽略一些url地址，对其不进行校验，通常用在一些静态文件中。
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin().loginPage("/login").loginProcessingUrl("/action").usernameParameter("username")
				.passwordParameter("password").successHandler(mySuccessHandler).failureHandler(myFailureHandler).permitAll();
		http.logout().logoutUrl("/logout").logoutSuccessHandler((req, res, authentication) -> {
			res.setContentType("application/json;charset=utf-8");
			PrintWriter out = res.getWriter();
			out.write("注销成功");
			out.flush();
			out.close();
		}).permitAll();
		http.exceptionHandling().authenticationEntryPoint((req, res, authException) -> {
			res.setContentType("application/json;charset=utf-8");
			PrintWriter out = res.getWriter();
			out.write("检测到未登录状态，请先登录");
			out.flush();
			out.close();
		});
		http.csrf().disable();
	}
}
