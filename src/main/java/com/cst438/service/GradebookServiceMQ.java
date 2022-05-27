package com.cst438.service;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cst438.domain.CourseDTOG;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;


public class GradebookServiceMQ extends GradebookService {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	Queue gradebookQueue;
	
	
	public GradebookServiceMQ() {
		System.out.println("MQ grade book service");
	}
	
	// send message to grade book service about new student enrollment in course
	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		 
		//TODO  complete this method in homework 4
		EnrollmentDTO e = new EnrollmentDTO(student_email, student_name, course_id);
		System.out.println("Sending rabbitmq message: " + e);
        rabbitTemplate.convertAndSend(gradebookQueue.getName(), e);
        System.out.println("Message sent.");
	}
	
	@RabbitListener(queues = "registration-queue")
	@Transactional
	public void receive(CourseDTOG courseDTOG) {
		
		//TODO  complete this method in homework 4
		System.out.println("Message received " + courseDTOG);
		System.out.println("GRADES " + courseDTOG.grades);
		for(CourseDTOG.GradeDTO g : courseDTOG.grades) {
			String grade = g.grade;
	        String student_email = g.student_email;
	        Enrollment e = enrollmentRepository.findByEmailAndCourseId(student_email, courseDTOG.course_id);
	        e.setCourseGrade(grade);
	        enrollmentRepository.save(e);
		}
	}
}
