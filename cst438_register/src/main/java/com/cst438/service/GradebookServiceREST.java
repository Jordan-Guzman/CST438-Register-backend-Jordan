package com.cst438.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.StudentDTO;


public class GradebookServiceREST extends GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	String gradebook_url;
	
	public GradebookServiceREST() {
		System.out.println("REST grade book service");
	}

	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		
		//TODO  complete this method in homework 4
		EnrollmentDTO e = new EnrollmentDTO(student_email, student_name, course_id);
		System.out.println("Sending http message: " + e);
		ResponseEntity<EnrollmentDTO> response = restTemplate.postForEntity(
				gradebook_url + "/enrollment", 
				e, 
				EnrollmentDTO.class);
		System.out.println("Sent");
		HttpStatus rc = response.getStatusCode();
		System.out.println("HttpStatus: " + rc);
		EnrollmentDTO returnObject = response.getBody();
		System.out.println(returnObject);
	}
	
}

