package edu.lxq.enro.security.config;

import java.nio.file.AccessDeniedException;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import edu.lxq.enro.security.UserDetailsRepository;

public class UserDetailsServiceConfiguration {
	@Bean
	public UserDetailsRepository userDetailsRepository() {
		UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
		UserDetails viix = User.withUsername("viix").password("{noop}123").authorities(AuthorityUtils.NO_AUTHORITIES)
				.build();
		userDetailsRepository.createUser(viix);
		return userDetailsRepository;
	}

	@Bean
	public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository) {
		return new UserDetailsManager() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userDetailsRepository.loadUserByUserName(username);
			}

			@Override
			public boolean userExists(String username) {
				return userDetailsRepository.userExists(username);
			}

			@Override
			public void updateUser(UserDetails user) {
				userDetailsRepository.updateUser(user);

			}

			@Override
			public void deleteUser(String username) {
				userDetailsRepository.deleteUser(username);

			}

			@Override
			public void createUser(UserDetails user) {
				userDetailsRepository.createUser(user);

			}

			@Override
			public void changePassword(String oldPassword, String newPassword) {
				try {
					userDetailsRepository.changePassword(oldPassword, newPassword);
				} catch (AccessDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
	}
}
