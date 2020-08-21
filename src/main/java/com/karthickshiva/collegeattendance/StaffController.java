package com.karthickshiva.collegeattendance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StaffController {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AttendanceEntryRepository attendanceEntryRepository;
	
	@GetMapping("/attendance")
	public String takeAttendance(Model model, @RequestParam(required = false) Integer classID) {
		model.addAttribute("classrooms", classRepository.findAll());
		model.addAttribute("subjects", subjectRepository.findAll());
		model.addAttribute("view", "takeAttendance");
		List<Student> students = new ArrayList<>();

		if (classID == null) {
			classID = classRepository.findAll().iterator().next().getId();
		}

		students.addAll(studentRepository.findByStudentClassId(classID));
		Collections.sort(students, Comparator.comparing(s -> s.getName()));
		model.addAttribute("classID", classID);
		model.addAttribute("students", students);
		return "index";
	}

	@PostMapping("/attendance/add")
	public String takeNewAttendance(Model model, @RequestParam Integer classID, @RequestParam Integer subjectID,
			@RequestParam Integer period, @RequestParam List<Integer> studentIDs, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		Class classRoom = classRepository.findById(classID).get();
		Subject subject = subjectRepository.findById(subjectID).get();
		Iterable<Student> students = studentRepository.findAllById(studentIDs);
		
		for(Student student : students)
		{
			AttendanceEntry attendanceEntry = attendanceEntryRepository.findByDateAndClassRoomAndSubjectAndStudent(date, classRoom, subject, student);
			if(attendanceEntry != null)
			{
				attendanceEntryRepository.delete(attendanceEntry);
			}
			attendanceEntry = new AttendanceEntry(classRoom, student, subject, date);
			attendanceEntryRepository.save(attendanceEntry);
		}
		return "redirect:/attendance";
	}
	
	@GetMapping("/attendance/report")
	public ResponseEntity<InputStreamResource> downloadAttendanceReport(@RequestParam Integer classID) throws IOException
	{
		List<AttendanceEntry> attendanceEntries = attendanceEntryRepository.findByClassRoomId(classID);
		List<Student> students = studentRepository.findByStudentClassId(classID);
		InputStreamResource resource = ReportUtil.downloadReportAsCSV(attendanceEntries, students);
		MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "test.xlsx")
				.contentType(mediaType)
				.body(resource);
	}

}
