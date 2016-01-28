package com.mxleague.boot.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import com.mxleague.boot.domain.Role;
import com.mxleague.boot.domain.User;
import com.mxleague.boot.repo.RoleRepo;
import com.mxleague.boot.repo.UserRepo;

@RestController
@RequestMapping("/rest/v1/users")
public class UserRestController {
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;
	
	private void updateRolePerUserOnceResourcewithLinks(User user, List<Role> roles){
		Role role = user.getRole();
		if(roles.contains(role)){
			role.add(linkTo(methodOn(RoleRestController.class).getAll()).slash(role.getId_role()).withSelfRel());
			roles.remove(role);
		}
	}
	
	private void updateUserResourcewithLinks(User user){
		user.add(linkTo(methodOn(UserRestController.class).getAll()).slash(user.getId_user()).withSelfRel());
	}
	
	private void updateRoleResourcewithLinks(User user){
		user.add(linkTo(methodOn(RoleRestController.class).getAll()).slash(user.getRole().getId_role()).withRel("role"));
	}

	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getSupportedMethods() {
		return ResponseEntity.status(200).allow(HttpMethod.values()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody User input) {
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
					.path("/rest/v1/users/{id}").buildAndExpand(newUser.getId_user()).toUri()).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getAll() {
		Iterable<User> users = userRepo.findAll();
		List<Role> roles = (List<Role>)roleRepo.findAll();
		for (User user : users) {
			updateUserResourcewithLinks(user);
			updateRolePerUserOnceResourcewithLinks(user,roles);
		}
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("id") String id) {
		User user = userRepo.findOne(id);
		updateUserResourcewithLinks(user);
		updateRoleResourcewithLinks(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
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
