package com.in28minutes.productcatalogueservice;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductCatalogueController {
	
	@Autowired
	private ProductCatalogueRepo repo;

	@GetMapping("/products/list-all")
	public List<ProductCatalogue> getAllProdicts() {
		return repo.findAll();	
	}
	
	@GetMapping("/products/{id}")
	public Optional<ProductCatalogue> getOne(@PathVariable Long id) {
		return repo.findById(id);
	}
	
	/*
	@PostMapping("/products/")
	public void AddProduct(@RequestBody ProductCatalogue product) {
		repo.save(product);
	} */
	
	@PostMapping("/products/")
	public ResponseEntity<ProductCatalogue> AddProduct(@RequestBody ProductCatalogue product) {
		ProductCatalogue savedProduct = repo.save(product);

		URI location = ServletUriComponentsBuilder
		.fromCurrentServletMapping()
		.path("{id}")
		.buildAndExpand(savedProduct.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	} 
}