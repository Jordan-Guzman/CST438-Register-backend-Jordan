package com.cst438.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int admin_id;
	private String name;
	private String email;
	
	public Admin() {
		super();
	}
	
	public int getAdminId() {
		return admin_id;
	}
	
	public void setAdminId(int admin_id) {
		this.admin_id = admin_id;
	}
	
	public String getAdminName() {
		return name;
	}
	
	public void setAdminName(String name) {
		this.name = name;
	}
	
	public String getAdminEmail() {
		return email;
	}
	
	public void setAdminEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Admin [admin_id=" + admin_id + ", name=" + name + ", email=" + email + "]";
	}
}
