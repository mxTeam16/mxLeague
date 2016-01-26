package com.mxleague.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mxleague.boot.domain.User;
import com.mxleague.boot.repo.UserRepo;

@RestController
@RequestMapping("/rest/v1/users")
public class UserRestController {
	@Autowired
	UserRepo userRepo;

	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getSupportedMethods() {
		return ResponseEntity.status(200).allow(HttpMethod.values()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody User input) {
		User newUser = userRepo.save(input);
		return ResponseEntity.status(HttpStatus.CREATED).location(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newUser.getId_user()).toUri()).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddById(@PathVariable("id") String id, @RequestBody User input) {
		boolean exists = (userRepo.findOne(id) != null ? true : false);
		if (input.getName() == null) {
			return ResponseEntity.status(400).body("Please provide the user name !!");
		}
		if (input.getPassword() == null) {
			return ResponseEntity.status(400).body("Please provide the password !!");
		}
		if (input.getRole() == null) {
			return ResponseEntity.status(400).body("Please provide the role !!");
		}
		input.setId_user(id);
		User newUser = userRepo.save(input);
		if (exists)
			return ResponseEntity.ok().body("User succesfully updated");
		else
			return ResponseEntity.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/v1/employees/{id}").buildAndExpand(newUser.getId_user()).toUri()).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAll() {
		return (List<User>) userRepo.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getById(@PathVariable("id") String id) {
		return userRepo.findOne(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		User found = userRepo.findOne(id);
		if (found != null) {
			userRepo.delete(found);
			return ResponseEntity.ok().body("User " + id + " succesfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
