package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;


@SpringBootTest
public class EndToEndRegisterAddStudentTest {
	
	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";
	
	public static final String URL = "https://cst438-register-fe-guzman.herokuapp.com";
	
	public static final int TEST_STUDENT_ID = 3;
	
	public static final String TEST_STUDENT_NAME = "test_student_1";
	
	public static final String TEST_STUDENT_EMAIL = "testing@student.test";
	
	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	StudentRepository studentRepository;
	
	@Test
	public void addStudentTest() throws Exception {
		
		/*
		 * if student is already exists, then delete the student.
		 */
		
		Student x = null;
		
		do {
			x = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
			if (x != null)
				studentRepository.delete(x);
		} while (x != null);

		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		try {
			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);
			
			// Locate and click "Add Student" button
			driver.findElement(By.xpath("//button[@id='main_add_student']")).click();
			Thread.sleep(SLEEP_DURATION);
			
			// Enter student name, student email, and click add button
			driver.findElement(By.xpath("//input[@type='text' and @name='name']")).sendKeys(TEST_STUDENT_NAME);
			driver.findElement(By.xpath("//input[@type='text' and @name='email']")).sendKeys(TEST_STUDENT_EMAIL);
			driver.findElement(By.xpath("//button[@id='add_student']")).click();
			Thread.sleep(SLEEP_DURATION);
			
			
			/*
			* verify that new student shows in student table in database.
			*/ 
			boolean found = false;
			Student student = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
			System.out.print("--------------STUDENT EMAIL: " + student.getEmail() + "-----------------------");
			if((student.getEmail().equals(TEST_STUDENT_EMAIL))) {
				found = true;
			}
			assertTrue(found, "Student added but not listed in the schedule");
			assertNotNull(student, "Student not found in database");
		} catch (Exception ex) {
			throw ex;
		} finally {
			
			// clean up database
			Student s = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
			if(s != null)
				studentRepository.delete(s);
			
			driver.quit();
		}
	}
}