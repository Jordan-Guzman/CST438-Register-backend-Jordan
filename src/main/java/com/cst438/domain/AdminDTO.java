package com.cst438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AdminDTO {
	public int admin_id;
	public String name;
	public String email;
	
	public AdminDTO(int id, String name, String email) {
		this.admin_id = id;
		this.name = name;
		this.email = email;
	}

	@Override
	public String toString() {
		return "AdminDTO [admin_id=" + admin_id + ", name=" +  name + ", email=" + email + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		AdminDTO other = (AdminDTO) obj;
		if (admin_id != other.admin_id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		
		return true;
	}
}
