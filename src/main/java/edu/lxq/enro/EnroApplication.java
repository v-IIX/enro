package edu.lxq.enro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class EnroApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnroApplication.class, args);
	}

}
