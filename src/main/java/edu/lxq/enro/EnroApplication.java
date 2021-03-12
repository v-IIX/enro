package edu.lxq.enro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("edu.lxq.enro.Mapper")
@SpringBootApplication()
public class EnroApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnroApplication.class, args);
	}

}
