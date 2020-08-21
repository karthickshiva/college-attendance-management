package com.karthickshiva.collegeattendance;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AttendanceEntryRepository extends CrudRepository<AttendanceEntry, Integer> {
	public AttendanceEntry findByDateAndClassRoomAndSubjectAndStudent(Date date, Class classRoom, Subject subject, Student student);
	public List<AttendanceEntry> findByClassRoomId(Integer classRoomId);
}
