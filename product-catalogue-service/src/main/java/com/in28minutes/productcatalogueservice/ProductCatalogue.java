package com.in28minutes.productcatalogueservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductCatalogue {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="product_name")
	private String productName;
	
	private String description;
	
	
	private ProductCatalogue(){
	}
	
	private ProductCatalogue(long id, String name,String desc) {
		this.id = id;
		this.productName = name;
		this.description = desc;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
