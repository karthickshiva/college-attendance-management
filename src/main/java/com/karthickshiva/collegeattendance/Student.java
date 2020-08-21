package com.karthickshiva.collegeattendance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String rollNumber, name;

	@OneToOne
	private Class studentClass;
	
	public Student() {}

	public Student(String rollNumber, String name, Class studentClass)
	{
		this.rollNumber = rollNumber;
		this.name = name;
		this.studentClass = studentClass;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(Class studentClass) {
		this.studentClass = studentClass;
	}

	public Integer getId() {
		return id;
	}	
	
	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
}
