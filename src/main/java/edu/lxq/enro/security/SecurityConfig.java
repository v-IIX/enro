package edu.lxq.enro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MySuccessHandler mySuccessHandler;
	private MyFailureHandler myFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/service1", "/service2")
		    .hasAnyAuthority("ROLE_user", "ROLE_admin").antMatchers("/log", "/user").hasAnyRole("admin").anyRequest()
		    .authenticated();
		http.formLogin().loginPage("/login").loginProcessingUrl("/index").usernameParameter("username")
		    .passwordParameter("password")
		    /* .defaultSuccessUrl("/index") */.successHandler(mySuccessHandler)
		    .failureHandler(myFailureHandler);
		http.logout().logoutSuccessUrl("/index");
		http.csrf().disable();

		// 取消认证
		//http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().logout().permitAll();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("user").password(new BCryptPasswordEncoder().encode("123")).roles("user")
		    .and().withUser("admin").password(new BCryptPasswordEncoder().encode(("123"))).roles("admin").and()
		    .passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/layui/css/**", "/layui/fonts/**", "/layui/img/**", "/layui/**");
	}
}
