package com.in28minutes.productcatalogueservice.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	public UserDaoSerivce service;
	
	@GetMapping("/product-users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	/*
	@GetMapping("/product-users/{id}")
	public User retrieveUser(@PathVariable int id) {
		return service.findOne(id);
	}
	*/
	/*
	@GetMapping("/product-users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id - "+id);
		}
		return user;
	} */
	
	/***
	 * HATEOAS - Hypermedia As The Engine of Application State
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/product-users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("id - "+id);
		}
		
		WebMvcLinkBuilder linkToUsers  = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
				
		EntityModel<User> model = EntityModel.of(user);
			model.add(linkToUsers.withRel("list-all-users"));
			
			return model;
	}
	
	//Created
	//input - details of user
	//output - CREATED and Return the created URL
 /*
	@PostMapping("/product-users/")
	public void createUser(@RequestBody User user) {
		service.save(user);
	} */
	
	@PostMapping("/product-users/")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
      User savedUser  =	service.save(user);
      
      // Take the current URL /users/ and append 4 = /users/4 
      // /users/{id} we can get the id from saveduser
      
      URI location = ServletUriComponentsBuilder
    		  .fromCurrentRequest().path("/{id}")
    		  .buildAndExpand(savedUser.getId()).toUri();
      
      	return ResponseEntity.created(location).build();
    }
	
	
	/*
	@DeleteMapping("/product-users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	*/
	
	@DeleteMapping("/product-users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) {
		User deletedUser = service.deleteById(id);
		if(deletedUser == null) {
			throw new UserNotFoundException("id -"+id);
		}
		return ResponseEntity.noContent().build();
	}
	

}
