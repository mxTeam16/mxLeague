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

import com.mxleague.boot.domain.Transfer;
import com.mxleague.boot.domain.Player;
import com.mxleague.boot.domain.Role;
import com.mxleague.boot.repo.TransferRepo;
import com.mxleague.boot.repo.UserRepo;
import com.mxleague.boot.repo.PlayerRepo;
import com.mxleague.boot.repo.RoleRepo;

@RestController
@RequestMapping("/rest/v1/transfers")
public class TransferRestController {
	@Autowired
	TransferRepo transferRepo;

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
	public ResponseEntity<?> add(@RequestBody Transfer input) {
		Transfer newTransfer = transferRepo.save(input);
		return ResponseEntity.status(HttpStatus.CREATED).location(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newTransfer.getId_transfer()).toUri()).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddById(@PathVariable("id") String id, @RequestBody Transfer input) {
		boolean exists = (transferRepo.findOne(id) != null ? true : false);
		if (input.getPlayer() == null) {
			return ResponseEntity.status(400).body("Please provide the player !!");
		}
		input.setId_transfer(id);
		Transfer newTransfer = transferRepo.save(input);
		if (exists)
			return ResponseEntity.ok().body("Transfer successfully updated");
		else
			return ResponseEntity
					.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
							.path("/rest/v1/transfers/{id}").buildAndExpand(newTransfer.getId_transfer()).toUri())
					.build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Transfer>> getAll() {
		Iterable<Transfer> transfers = transferRepo.findAll();
		List<Role> roles = (List<Role>) roleRepo.findAll();
		for (Transfer transfer : transfers) {
			Link.updateTransferResourcewithLinks(transfer);
			Link.updatePlayerResourcewithLinks(transfer.getPlayer());
			Link.updateUserResourcewithLinks(transfer.getPlayer().getUser());
			Link.updateRolePerUserOnceResourcewithLinks(transfer.getPlayer().getUser().getRole(), roles);
		}
		return new ResponseEntity<Iterable<Transfer>>(transfers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Transfer> getById(@PathVariable("id") String id) {
		Transfer transfer = transferRepo.findOne(id);
		if (transfer != null) {
			Link.updateTransferResourcewithLinks(transfer);
			Link.updatePlayerResourcewithLinks(transfer.getPlayer());
			Link.updateUserResourcewithLinks(transfer.getPlayer().getUser());
			Link.updateRoleResourcewithLinks(transfer.getPlayer().getUser().getRole());
			return new ResponseEntity<Transfer>(transfer, HttpStatus.OK);
		} else
			return new ResponseEntity<Transfer>(transfer, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{transferId}/{playerId}", method = RequestMethod.GET)
	public ResponseEntity<Player> getPlayerByTransfer(@PathVariable("transferId") String transferId,
			@PathVariable("playerId") String playerId) {
		Player player = playerRepo.findById_PlayerAndId_TransferCaseInsensitive(playerId, transferId);
		if (player != null) {
			Link.updatePlayerPerTransferResourcewithLinks(player);
			Link.updateUserResourcewithLinks(player.getUser());
			Link.updateRoleResourcewithLinks(player.getUser().getRole());
			return new ResponseEntity<Player>(player, HttpStatus.OK);
		} else
			return new ResponseEntity<Player>(player, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Transfer found = transferRepo.findOne(id);
		if (found != null) {
			transferRepo.delete(found);
			return ResponseEntity.ok().body("Transfer " + id + " successfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
