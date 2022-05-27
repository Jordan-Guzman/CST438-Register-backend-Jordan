package com.cst438.domain;

public class StudentDTO {
	public int student_id;
	public String name;
	public String email;
	public String status;
	public int status_code;
//	public int course_id;
	
	@Override
	public String toString() {
		return "StudentDTO [student_id=" + student_id + "name=" + name + "email=" + email + "]";
	}
	
//	public String toString() {
//		return "StudentDTO [student_id=" + student_id + "name=" + name + "email=" + email + "course_id=" + course_id + "]";
//	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentDTO other = (StudentDTO) obj;
		if (student_id != other.student_id)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status_code != 1 || status_code != 0) {			
			if (other.status_code != 1 || other.status_code != 0)
				return false;
		} else if (status_code != other.status_code)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
//		if (course_id == 0) {
//			if (other.course_id == 0)
//				return false;
//		} else if (course_id != other.course_id)
//			return false;
		return true;
	}
}