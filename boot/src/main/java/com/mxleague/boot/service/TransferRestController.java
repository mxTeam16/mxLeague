package com.mxleague.boot.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
import com.mxleague.boot.repo.TransferRepo;
import com.mxleague.boot.repo.PlayerRepo;

@RestController
@RequestMapping("/rest/v1/transfers")
public class TransferRestController {
	@Autowired
	TransferRepo transferRepo;

	@Autowired
	PlayerRepo playerRepo;

	private void updateTransferResourcewithLinks(Transfer transfer) {
		transfer.add(linkTo(methodOn(TransferRestController.class).getAll()).slash(transfer.getId_transfer())
				.withSelfRel());
	}

	private void updatePlayerResourcewithLinks(Player player) {
		player.add(linkTo(methodOn(TransferRestController.class).getAll()).slash(player.getId_player()).withSelfRel());
	}

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
			return ResponseEntity.ok().body("Transfer succesfully updated");
		else
			return ResponseEntity
					.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
							.path("/rest/v1/transfers/{id}").buildAndExpand(newTransfer.getId_transfer()).toUri())
					.build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Transfer>> getAll() {
		Iterable<Transfer> transfers = transferRepo.findAll();
		for (Transfer transfer : transfers) {
			updateTransferResourcewithLinks(transfer);
		}
		return new ResponseEntity<Iterable<Transfer>>(transfers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Transfer> getById(@PathVariable("id") String id) {
		Transfer transfer = transferRepo.findOne(id);
		if (transfer != null) {
			updateTransferResourcewithLinks(transfer);
			return new ResponseEntity<Transfer>(transfer, HttpStatus.OK);
		} else
			return new ResponseEntity<Transfer>(transfer, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{transferId}/{playerId}", method = RequestMethod.GET)
	public ResponseEntity<Player> getPlayerByTransfer(@PathVariable("transferId") String transferId,
			@PathVariable("playerId") String playerId) {
		Player player = playerRepo.findById_PlayerAndId_TransferCaseInsensitive(playerId, transferId);
		if (player != null) {
			updateTransferResourcewithLinks(player.getTransfer());
			updatePlayerResourcewithLinks(player);
			return new ResponseEntity<Player>(player, HttpStatus.OK);
		} else
			return new ResponseEntity<Player>(player, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Transfer found = transferRepo.findOne(id);
		if (found != null) {
			transferRepo.delete(found);
			return ResponseEntity.ok().body("Transfer " + id + " succesfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
