package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.CourseDTOG;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;

@RestController
public class CourseController {
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	/*
	 * endpoint used by gradebook service to transfer final course grades
	 */
	@PutMapping("/course/{course_id}")
	@Transactional
	public void updateCourseGrades( @RequestBody CourseDTOG courseDTO, @PathVariable("course_id") int course_id) {
		
		//TODO  complete this method in homework 4
		
		/**
		 * go into enrollment table
		 * retrieve student enrollment entity object
		 * update the grade
		 * save it back into the enrollment repository
		 */
        System.out.println("GRADES " + courseDTO.grades);
        for(CourseDTOG.GradeDTO g : courseDTO.grades) {
        	String grade = g.grade;
        	String student_email = g.student_email;
        	Enrollment e = enrollmentRepository.findByEmailAndCourseId(student_email, course_id);
        	e.setCourseGrade(grade);
        	enrollmentRepository.save(e);
        }
        
	}

}