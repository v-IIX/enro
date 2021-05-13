package edu.lxq.enro.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import edu.lxq.enro.mybatis.StudentMapper;

@Component
public class GetStudentMapper {
	@Bean
	public StudentMapper getMapper() {
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
		return studentMapper;
	}
}
