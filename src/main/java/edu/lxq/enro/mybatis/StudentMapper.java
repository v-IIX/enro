package edu.lxq.enro.mybatis;

import org.springframework.stereotype.Component;

public interface StudentMapper {
	Student findAllStudents();

	Student findStudentById(Integer id);

	void insertStudent(Student student);
}