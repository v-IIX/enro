package edu.lxq.enro;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.lxq.enro.dao.GetStudentMapper;
import edu.lxq.enro.mybatis.StudentMapper;
import edu.lxq.enro.security.MyUserDetails;

@SpringBootTest
class EnroApplicationTests {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EnroApplicationTests.class);
	@Autowired
	private GetStudentMapper studentMapper;

	@Test
	void contextLoads() throws IOException {
		/*
		 * String resource = "edu/lxq/enro/mybatis/mybatis_config.xml"; InputStream
		 * inputStream = Resources.getResourceAsStream(resource); SqlSessionFactory
		 * sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); //
		 * 然后根据 sqlSessionFactory 得到 session SqlSession session =
		 * sqlSessionFactory.openSession(); // 模糊查询 // List<Student> listStudent =
		 * session.selectList("findAllStudents"); // 使用mapper方式 StudentMapper
		 * studentMapper = session.getMapper(StudentMapper.class);
		 */
		MyUserDetails student = studentMapper.getMapper().findByUserName("zhang3");
		System.out.println(student.getUsername());
		List<String> roles = studentMapper.getMapper().findRoleByUserName("zhang3");
		List<String> permissions = studentMapper.getMapper().findAuthorityByRoleCodes(roles);
		List<String> urls = studentMapper.getMapper().findUrlsByUserName("zhang3");
		//List<String> urls = stuMapper.findUrlsByUserName("zhang3");

		roles = roles.stream().map(rc -> "ROLE_" + rc) // 每个对象前加前缀
				.collect(Collectors.toList()); // 再转换回List

		for (String role : roles) {
			System.out.println("roles:" + role);
		}
		
		for (String url : urls) {
			System.out.println("urls:" + url);
		}
		
		permissions.addAll(roles); // 添加修改好前缀的角色前缀的角色权限
		for (String permission : permissions) {
			System.out.println("permissions:" + permission);
		}
	}

}
