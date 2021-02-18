package edu.lxq.enro.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.lxq.enro.mybatis.StudentMapper;

public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String resource = "edu/lxq/enro/mybatis/mybatis_config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 然后根据 sqlSessionFactory 得到 session
		SqlSession session = sqlSessionFactory.openSession();
		// 模糊查询
		StudentMapper studentMapper = session.getMapper(StudentMapper.class);

		MyUserDetails myUserDetails = studentMapper.findByUserName(username);
		List<String> roleCodes = studentMapper.findRoleByUserName(username);
		return myUserDetails;
	}

}
