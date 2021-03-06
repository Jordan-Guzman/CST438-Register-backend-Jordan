package com.cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Admin;
import com.cst438.domain.AdminDTO;
import com.cst438.domain.AdminRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class AdminController {

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/admin")
	public AdminDTO getAdmin( @RequestParam("email") String email, @AuthenticationPrincipal OAuth2User principal ) {
		
		String admin_email = principal.getAttribute("email");
		Admin admin = adminRepository.findByEmail(admin_email);
		
		if (admin != null) {
			System.out.println(admin.toString());
			return createAdminDTO(admin);
		} else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Admin not found. ");
		}
	}
	
	@PostMapping("/student")
	@Transactional
	public StudentDTO addStudent( @RequestBody StudentDTO studentDTO, @AuthenticationPrincipal OAuth2User principal ) {

		String student_email = principal.getAttribute("email");
		Student studentCreds = studentRepository.findByEmail(student_email);
		
		if (studentCreds == null) {
			Student student = new Student();
			student.setName(studentDTO.name);
			student.setEmail(studentDTO.email);
			Student savedStudent = studentRepository.save(student);
			StudentDTO result = createStudentDTO(savedStudent);
			return result;
		} else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student already exists.  ");
		}
		
	}
	
	@PutMapping("/student/{student_id}")
	@Transactional
	public void updateStatus( @PathVariable int student_id, @RequestBody StudentDTO studentDTO ) {
		
		Student student = studentRepository.findById(student_id);
		
		if(student != null) {
			student.setStatusCode(studentDTO.status_code);
			studentRepository.save(student);
		}
		else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student ID does not exist. " + student_id);
		}
	}
	
	@DeleteMapping("/student/{student_id}")
	@Transactional
	public void deleteStudent(  @PathVariable int student_id, @AuthenticationPrincipal OAuth2User principal  ) {
		
		String student_email = principal.getAttribute("email");   // student's email 
		
		Student student = studentRepository.findById(student_id);
		
		// verify that student is enrolled in the course.
		if (student!=null && student.getEmail().equals(student_email)) {
			// OK.  drop the course.
			 studentRepository.delete(student);
		} else {
			// something is not right with the enrollment.  
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Enrollment_id invalid. "+student_id);
		}
	}
	
	private AdminDTO createAdminDTO(Admin a) {
		AdminDTO adminDTO = new AdminDTO();
		adminDTO.admin_id = a.getAdminId();
		adminDTO.name = a.getAdminName();
		adminDTO.email = a.getAdminEmail();
		return adminDTO;
	}
	
	private StudentDTO createStudentDTO(Student s) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.student_id = s.getStudent_id();
		studentDTO.name = s.getName();
		studentDTO.email = s.getEmail();
		studentDTO.status = s.getStudentStatus();
		studentDTO.status_code = s.getStatusCode();
		return studentDTO;
	}
}
