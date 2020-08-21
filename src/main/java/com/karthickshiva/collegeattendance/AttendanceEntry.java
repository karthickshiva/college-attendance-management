package com.karthickshiva.collegeattendance;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "date", "student_id", "subject_id"}))
@Entity
public class AttendanceEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne
	private Class classRoom;

	@OneToOne
	private Student student;

	@OneToOne
	private Subject subject;

	private Date date;
	
	public AttendanceEntry() {}

	public AttendanceEntry(Class classRoom, Student student, Subject subject, Date date) {
		this.classRoom = classRoom;
		this.student = student;
		this.subject = subject;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Class getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(Class classRoom) {
		this.classRoom = classRoom;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
