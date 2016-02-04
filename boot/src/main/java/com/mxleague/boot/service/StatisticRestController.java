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

import com.mxleague.boot.domain.Statistic;
import com.mxleague.boot.domain.User;
import com.mxleague.boot.domain.Player;
import com.mxleague.boot.domain.Role;
import com.mxleague.boot.repo.StatisticRepo;
import com.mxleague.boot.repo.UserRepo;
import com.mxleague.boot.repo.PlayerRepo;
import com.mxleague.boot.repo.RoleRepo;

@RestController
@RequestMapping("/rest/v1/statistics")
public class StatisticRestController {
	@Autowired
	StatisticRepo statisticRepo;

	@Autowired
	PlayerRepo playerRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;

	private void updateStatisticResourcewithLinks(Statistic statistic) {
		statistic.add(linkTo(methodOn(StatisticRestController.class).getAll()).slash(statistic.getId_statistic())
				.withSelfRel());
	}

	private void updatePlayerPerStatisticResourcewithLinks(Player player) {
		player.add(linkTo(methodOn(StatisticRestController.class).getAll()).slash(player.getStatistic().getId_statistic()).slash(player.getId_player()).withSelfRel());
	}
	
	private void updatePlayerResourcewithLinks(Player player) {
		player.add(linkTo(methodOn(PlayerRestController.class).getAll()).slash(player.getId_player()).withSelfRel());
	}
	
	private void updateUserResourcewithLinks(User user) {
		user.add(linkTo(methodOn(UserRestController.class).getAll()).slash(user.getId_user()).withSelfRel());
	}
	
	private void updateRolePerUserOnceResourcewithLinks(Role role, List<Role> roles) {
		if (roles.contains(role)) {
			role.add(linkTo(methodOn(RoleRestController.class).getAll()).slash(role.getId_role()).withSelfRel());
			roles.remove(role);
		}
	}
	
	private void updateRoleResourcewithLinks(User user) {
		user.getRole().add(
				linkTo(methodOn(RoleRestController.class).getAll()).slash(user.getRole().getId_role()).withSelfRel());
	}
	
	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getSupportedMethods() {
		return ResponseEntity.status(200).allow(HttpMethod.values()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Statistic input) {
		Statistic newStatistic = statisticRepo.save(input);
		return ResponseEntity.status(HttpStatus.CREATED).location(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newStatistic.getId_statistic()).toUri()).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddById(@PathVariable("id") String id, @RequestBody Statistic input) {
		boolean exists = (statisticRepo.findOne(id) != null ? true : false);
		if (input.getPlayer() == null) {
			return ResponseEntity.status(400).body("Please provide the player !!");
		}
		input.setId_statistic(id);
		Statistic newStatistic = statisticRepo.save(input);
		if (exists)
			return ResponseEntity.ok().body("Statistic succesfully updated");
		else
			return ResponseEntity
					.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
							.path("/rest/v1/statistics/{id}").buildAndExpand(newStatistic.getId_statistic()).toUri())
					.build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Statistic>> getAll() {
		Iterable<Statistic> statistics = statisticRepo.findAll();
		List<Role> roles = (List<Role>) roleRepo.findAll();
		for (Statistic statistic : statistics) {
			updateStatisticResourcewithLinks(statistic);
			updatePlayerResourcewithLinks(statistic.getPlayer());
			updateUserResourcewithLinks(statistic.getPlayer().getUser());
			updateRolePerUserOnceResourcewithLinks(statistic.getPlayer().getUser().getRole(), roles);
		}
		return new ResponseEntity<Iterable<Statistic>>(statistics, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Statistic> getById(@PathVariable("id") String id) {
		Statistic statistic = statisticRepo.findOne(id);
		if (statistic != null) {
			updateStatisticResourcewithLinks(statistic);
			updatePlayerResourcewithLinks(statistic.getPlayer());
			updateUserResourcewithLinks(statistic.getPlayer().getUser());
			updateRoleResourcewithLinks(statistic.getPlayer().getUser());
			return new ResponseEntity<Statistic>(statistic, HttpStatus.OK);
		} else
			return new ResponseEntity<Statistic>(statistic, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{statisticId}/{playerId}", method = RequestMethod.GET)
	public ResponseEntity<Player> getPlayerByStatistic(@PathVariable("statisticId") String statisticId,
			@PathVariable("playerId") String playerId) {
		Player player = playerRepo.findById_PlayerAndId_StatisticCaseInsensitive(playerId, statisticId);
		if (player != null) {
			updatePlayerPerStatisticResourcewithLinks(player);
			updateUserResourcewithLinks(player.getUser());
			updateRoleResourcewithLinks(player.getUser());
			return new ResponseEntity<Player>(player, HttpStatus.OK);
		} else
			return new ResponseEntity<Player>(player, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Statistic found = statisticRepo.findOne(id);
		if (found != null) {
			statisticRepo.delete(found);
			return ResponseEntity.ok().body("Statistic " + id + " succesfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
