package edu.lxq.enro.mybatis;

import java.util.List;

import org.springframework.stereotype.Component;

import edu.lxq.enro.security.MyUserDetails;

@Component
public interface StudentMapper {
	List<Student> findAllStudents();

	Student findStudentById(Integer id);

	MyUserDetails findByUserName(String userName);

	List<String> findRoleByUserName(String userName);

	List<String> findAuthorityByRoleCodes();

	void insertStudent(Student student);
}