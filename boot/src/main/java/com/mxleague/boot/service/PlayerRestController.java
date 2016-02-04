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

import com.mxleague.boot.domain.Player;
import com.mxleague.boot.domain.Role;
import com.mxleague.boot.domain.User;
import com.mxleague.boot.repo.PlayerRepo;
import com.mxleague.boot.repo.RoleRepo;
import com.mxleague.boot.repo.UserRepo;

@RestController
@RequestMapping("/rest/v1/players")
public class PlayerRestController {
	@Autowired
	PlayerRepo playerRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getSupportedMethods() {
		return ResponseEntity.status(200).allow(HttpMethod.values()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Player input) {
		Player newPlayer = playerRepo.save(input);
		return ResponseEntity.status(HttpStatus.CREATED).location(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newPlayer.getId_player()).toUri()).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddById(@PathVariable("id") String id, @RequestBody Player input) {
		boolean exists = (playerRepo.findOne(id) != null ? true : false);
		if (input.getUser() == null) {
			return ResponseEntity.status(400).body("Please provide the user !!");
		}
		input.setId_player(id);
		Player newPlayer = playerRepo.save(input);
		if (exists)
			return ResponseEntity.ok().body("Player successfully updated");
		else
			return ResponseEntity.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/rest/v1/players/{id}").buildAndExpand(newPlayer.getId_player()).toUri()).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Player>> getAll() {
		Iterable<Player> players = playerRepo.findAll();
		List<Role> roles = (List<Role>) roleRepo.findAll();
		for (Player player : players) {
			Link.updatePlayerResourcewithLinks(player);
			Link.updateUserResourcewithLinks(player.getUser());
			Link.updateRolePerUserOnceResourcewithLinks(player.getUser().getRole(), roles);
		}
		return new ResponseEntity<Iterable<Player>>(players, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Player> getById(@PathVariable("id") String id) {
		Player player = playerRepo.findOne(id);
		if (player != null) {
			Link.updatePlayerResourcewithLinks(player);
			Link.updateUserResourcewithLinks(player.getUser());
			Link.updateRoleResourcewithLinks(player.getUser().getRole());
			return new ResponseEntity<Player>(player, HttpStatus.OK);
		} else
			return new ResponseEntity<Player>(player, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{playerId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByPlayer(@PathVariable("playerId") String playerId,
			@PathVariable("userId") String userId) {
		User user = userRepo.findById_UserAndId_PlayerCaseInsensitive(userId, playerId);
		if (user != null) {
			Link.updateUserPerPlayerResourcewithLinks(user);
			Link.updateRoleResourcewithLinks(user.getRole());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Player found = playerRepo.findOne(id);
		if (found != null) {
			playerRepo.delete(found);
			return ResponseEntity.ok().body("Player " + id + " successfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
