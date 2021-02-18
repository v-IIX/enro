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
import org.springframework.boot.test.context.SpringBootTest;

import edu.lxq.enro.mybatis.StudentMapper;
import edu.lxq.enro.security.MyUserDetails;

@SpringBootTest
class EnroApplicationTests {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EnroApplicationTests.class);

	@Test
	void contextLoads() throws IOException {
		String resource = "edu/lxq/enro/mybatis/mybatis_config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 然后根据 sqlSessionFactory 得到 session
		SqlSession session = sqlSessionFactory.openSession();
		// 模糊查询
		// List<Student> listStudent = session.selectList("findAllStudents");
		// 使用mapper方式
		StudentMapper studentMapper = session.getMapper(StudentMapper.class);
		MyUserDetails student = studentMapper.findByUserName("zhang3");
		System.out.println(student.getPassword());
		List<String> roles = studentMapper.findRoleByUserName("zhang3");

		roles = roles.stream().map(rc -> "ROLE_" + rc) // 每个对象前加前缀
				.collect(Collectors.toList()); // 再转换回List

		for (String role : roles) {
			System.out.println("roles:" + role);
		}

		List<String> permissions = studentMapper.findAuthorityByRoleCodes(roles);
		permissions.addAll(roles); // 添加修改好前缀的角色前缀的角色权限
		for (String permission : permissions) {
			System.out.println("permissions:" + permission);
		}

	}

}
