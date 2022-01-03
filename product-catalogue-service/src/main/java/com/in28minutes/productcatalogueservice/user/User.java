package com.in28minutes.productcatalogueservice.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message="Name should have atleast 2 characters")
	private String name;
	
	@Past
	private Date birthDate;
	
	//With latest jackson - default constructor is no longer needed.
	protected User() {
	}

	public User(Integer id, String name, Date bdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = bdate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfBirth() {
		return birthDate;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.birthDate = dateOfBirth;
	}
	
	
}
