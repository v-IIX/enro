package edu.lxq.enro;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.lxq.enro.mybatis.Student;
import edu.lxq.enro.mybatis.StudentMapper;

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
		List<Student> listStudent = session.selectList("findAllStudents");
		for (Student student : listStudent) {
			System.out.println("ID:" + student.getStudId() + ",NAME:" + student.getName());
		}
	}

}
