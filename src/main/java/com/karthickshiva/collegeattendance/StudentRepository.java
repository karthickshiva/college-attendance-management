package com.karthickshiva.collegeattendance;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
	public List<Student> findByStudentClassId(Integer classID);
}
