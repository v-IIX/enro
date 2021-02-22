package edu.lxq.enro.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.lxq.enro.dao.GetStudentMapper;
import edu.lxq.enro.mybatis.StudentMapper;

@Component
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private GetStudentMapper studentMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

		MyUserDetails myUserDetails = studentMapper.getMapper().findByUserName(username);
		List<String> roles = studentMapper.getMapper().findRoleByUserName(username);
		List<String> permissions = studentMapper.getMapper().findAuthorityByRoleCodes(roles);
		// 角色是一个特殊的权限，ROLE_前缀
		roles = roles.stream().map(rc -> "ROLE_" + rc) // 每个对象前加前缀
				.collect(Collectors.toList()); // 再转换回List
		permissions.addAll(roles); // 添加修改好前缀的角色前缀的角色权限

		// 把权限类型的权限给UserDetails
		myUserDetails.setAuthorities(
				// 逗号分隔的字符串转换成权限类型列表
				AuthorityUtils.commaSeparatedStringToAuthorityList(
						// List转字符串,逗号分隔
						String.join(",", permissions)));

		return myUserDetails;
	}

}
