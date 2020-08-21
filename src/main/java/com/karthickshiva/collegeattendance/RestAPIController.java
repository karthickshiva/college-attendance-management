package com.karthickshiva.collegeattendance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPIController {
	
	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudentRepository studentRepository;
	
	
	@GetMapping("/classes")
	public Iterable<Class> getClasses()
	{
		return classRepository.findAll();
	}
	
	@GetMapping("/subjects")
	public Iterable<Subject> getSubjects()
	{
		return subjectRepository.findAll();
	}
	
	@GetMapping("/students")
	public List<Student> getStudents(@RequestParam Integer classID)
	{
		return studentRepository.findByStudentClassId(classID);
	}
	
}
