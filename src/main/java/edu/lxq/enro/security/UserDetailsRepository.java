package edu.lxq.enro.security;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsRepository {
	private Map<String, UserDetails> users = new HashMap<>();

	public void createUser(UserDetails user) {
		users.putIfAbsent(user.getUsername(), user);
	}

	public void updateUser(UserDetails user) {
		users.put(user.getUsername(), user);
	}

	public void deleteUser(String userName) {
		users.remove(userName);
	}

	public void changePassword(String oldPassword, String newPassword) throws AccessDeniedException {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

		if (currentUser == null) {
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context " + "for current user.");
		}

		String username = currentUser.getName();
		UserDetails user = users.get(username);

		if (user == null) {
			throw new IllegalStateException("Current user doesn't exist in database.");
		}
	}

	public boolean userExists(String username) {
		return users.containsKey(username);
	}

	public UserDetails loadUserByUserName(String username) {
		return users.get(username);
	}

}
