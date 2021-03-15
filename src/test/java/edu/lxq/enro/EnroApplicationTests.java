package edu.lxq.enro;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.lxq.enro.config.Generator;
import edu.lxq.enro.dao.GetStudentMapper;
import edu.lxq.enro.entity.User;
import edu.lxq.enro.mapper.UserMapper;

@SpringBootTest
class EnroApplicationTests {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EnroApplicationTests.class);
	@Autowired
	private GetStudentMapper studentMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private Generator generator;

	@Test
	void contextLoads() throws IOException {
		List<User> uls = userMapper.selectList(null);
		for (User ul : uls) {
			System.out.println(ul);
		}
	}

}
