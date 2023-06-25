package com.ransankul.clickmart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Roles {
	//This table contain all the roles and their PRIMARY KEY

	@Id
	private	int id;
	private String role;
	
	public Roles() {}
	
	public Roles(int i, String string) {
		this.id = i;
		this.role = string;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
