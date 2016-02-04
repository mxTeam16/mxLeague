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

import com.mxleague.boot.domain.Role;
import com.mxleague.boot.domain.User;
import com.mxleague.boot.repo.RoleRepo;
import com.mxleague.boot.repo.UserRepo;

@RestController
@RequestMapping("/rest/v1/roles")
public class RoleRestController {
	@Autowired
	RoleRepo roleRepo;

	@Autowired
	UserRepo userRepo;

	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getSupportedMethods() {
		return ResponseEntity.status(200).allow(HttpMethod.values()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Role input) {
		Role newRole = roleRepo.save(input);
		return ResponseEntity.status(HttpStatus.CREATED).location(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newRole.getId_role()).toUri()).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddById(@PathVariable("id") String id, @RequestBody Role input) {
		boolean exists = (roleRepo.findOne(id) != null ? true : false);
		if (input.getGrant() == null) {
			return ResponseEntity.status(400).body("Please provide the grant !!");
		}
		input.setId_role(id);
		Role newRole = roleRepo.save(input);
		if (exists)
			return ResponseEntity.ok().body("Role successfully updated");
		else
			return ResponseEntity.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/rest/v1/roles/{id}").buildAndExpand(newRole.getId_role()).toUri()).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getAll() {
		Iterable<Role> roles = roleRepo.findAll();
		for (Role role : roles) {
			Link.updateRoleResourcewithLinks(role);
			Link.updateUsersPerRoleResourcewithLinks(role);
		}
		return new ResponseEntity<Iterable<Role>>(roles, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("id") String id) {
		Role role = roleRepo.findOne(id);
		if (role != null) {
			Link.updateRoleResourcewithLinks(role);
			Link.updateUsersPerRoleResourcewithLinks(role);
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} else
			return new ResponseEntity<Role>(role, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getUsersByRole(@PathVariable("id") String id) {
		Iterable<User> users = userRepo.findById_RoleCaseInsensitive(id);
		List<Role> roles = (List<Role>) roleRepo.findAll();
		for (User user : users) {
			Link.updateUserResourcewithLinks(user);
			Link.updateRolePerUserOnceResourcewithLinks(user.getRole(), roles);
		}
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/{roleId}/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeesById(@PathVariable("roleId") String roleId,
			@PathVariable("userId") String userId) {
		User user = userRepo.findById_UserAndId_RoleCaseInsensitive(userId, roleId);
		Link.updateUserResourcewithLinks(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Role found = roleRepo.findOne(id);
		if (found != null) {
			roleRepo.delete(found);
			return ResponseEntity.ok().body("Role " + id + " successfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
