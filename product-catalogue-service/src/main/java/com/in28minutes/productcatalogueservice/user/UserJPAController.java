package com.in28minutes.productcatalogueservice.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/product-users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
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
	@GetMapping("/jpa/product-users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) { //user.isPresent since it never be null
		  	throw new UserNotFoundException("id - "+id);
		}
		
		WebMvcLinkBuilder linkToUsers  = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
				
		EntityModel<User> model = EntityModel.of(user.get());
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
	
	@PostMapping("/jpa/product-users/")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
      User savedUser  =	userRepository.save(user);
      
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
	
	@DeleteMapping("/jpa/product-users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		
	}
	
	/**
	 * This methos is used to get all the post by a specific user
	 * @param id
	 * @return
	 */
	
	@GetMapping("/jpa/product-users/{id}/posts")
	public List<Post> retrieveAllUserPosts(@PathVariable Integer id){
		
	 Optional<User> userOptional =  userRepository.findById(id);
	 	
	 if(!userOptional.isPresent()) {
		 throw new UserNotFoundException("id -"+id);
	 }
	 
	 return userOptional.get().getPosts();
		 
	}

	/**
	 * 
	 */

	@PostMapping("/jpa/product-users/{id}/posts")
	public ResponseEntity<User> createPost(@PathVariable int id,@RequestBody Post post) {
      Optional<User> userOptional  =	userRepository.findById(id);
      
      if(!userOptional.isPresent()) {
    	  throw new UserNotFoundException("id - "+id);
      }
       
      User user = userOptional.get();
      post.setUser(user);
      postRepository.save(post);
 
      URI location = ServletUriComponentsBuilder
    		  .fromCurrentRequest().path("/{id}")
    		  .buildAndExpand(post.getId()).toUri();
      
      	return ResponseEntity.created(location).build();
    }
	
	
	
}
