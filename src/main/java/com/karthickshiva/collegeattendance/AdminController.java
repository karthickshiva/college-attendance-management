package com.karthickshiva.collegeattendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/")
	public String root(Model model) {
		model.addAttribute("view", "home");
		return "index";
	}

	@GetMapping("/class/add")
	public String addClass(Model model) {
		model.addAttribute("view", "addClass");
		return "index";
	}
	
	@GetMapping("/class/view")
	public String viewClass(Model model) {
		Iterable<Class> classes = classRepository.findAll();
		model.addAttribute("view", "viewClasses");
		model.addAttribute("classes", classes);
		return "index";
	}

	@GetMapping("/subject/add")
	public String addSubject(Model model) {
		Iterable<Class> classRooms = classRepository.findAll();
		model.addAttribute("classrooms", classRooms);
		model.addAttribute("view", "addSubject");
		return "index";
	}
	
	@GetMapping("/subject/view")
	public String viewSubject(Model model) {
		Iterable<Subject> subjects = subjectRepository.findAll();
		model.addAttribute("view", "viewSubjects");
		model.addAttribute("subjects", subjects);
		return "index";
	}

	@GetMapping("/student/add")
	public String addStudent(Model model) {
		Iterable<Class> classRooms = classRepository.findAll();
		model.addAttribute("classrooms", classRooms);
		model.addAttribute("view", "addStudent");
		return "index";
	}
	
	@GetMapping("/student/view")
	public String viewStudent(Model model,  @RequestParam(required = false) Integer classID) {
		model.addAttribute("classrooms", classRepository.findAll());
		model.addAttribute("view", "viewStudents");
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

	@PostMapping("/class/add")
	public String addNewClass(@RequestParam String name, Model model) {
		Class classRoom = new Class(name);
		model.addAttribute("view", "addClass");
		classRepository.save(classRoom);
		return "index";
	}

	@PostMapping("/subject/add")
	public String addNewSubject(@RequestParam String name, Model model) {
		Subject subject = new Subject(name);
		subjectRepository.save(subject);
		return addSubject(model);
	}

	@PostMapping("/student/add")
	public String addNewStudent(@RequestParam String rollNumber, @RequestParam String name,
			@RequestParam Integer classID, Model model) {
		Optional<Class> opt = classRepository.findById(classID);
		if (opt.isPresent()) {
			Class classRoom = opt.get();
			Student student = new Student(rollNumber, name, classRoom);
			studentRepository.save(student);
		}
		return addStudent(model);
	}

}
