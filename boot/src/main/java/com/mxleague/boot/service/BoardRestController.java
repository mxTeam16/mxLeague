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

import com.mxleague.boot.domain.Board;
import com.mxleague.boot.domain.Role;
import com.mxleague.boot.domain.User;
import com.mxleague.boot.repo.BoardRepo;
import com.mxleague.boot.repo.RoleRepo;
import com.mxleague.boot.repo.UserRepo;

@RestController
@RequestMapping("/rest/v1/board")
public class BoardRestController {
	@Autowired
	BoardRepo boardRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> getSupportedMethods() {
		return ResponseEntity.status(200).allow(HttpMethod.values()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Board input) {
		Board newMember = boardRepo.save(input);
		return ResponseEntity.status(HttpStatus.CREATED).location(ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newMember.getId_board()).toUri()).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAddById(@PathVariable("id") String id, @RequestBody Board input) {
		boolean exists = (boardRepo.findOne(id) != null ? true : false);
		if (input.getUser() == null) {
			return ResponseEntity.status(400).body("Please provide the user !!");
		}
		if (input.getJob() == null) {
			return ResponseEntity.status(400).body("Please provide the job !!");
		}
		input.setId_board(id);
		Board newMember = boardRepo.save(input);
		if (exists)
			return ResponseEntity.ok().body("Member of Board successfully updated");
		else
			return ResponseEntity.status(201).location(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/rest/v1/board/{id}").buildAndExpand(newMember.getId_board()).toUri()).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Board>> getAll() {
		Iterable<Board> board = boardRepo.findAll();
		List<Role> roles = (List<Role>) roleRepo.findAll();
		for (Board member : board) {
			Link.updateBoardResourcewithLinks(member);
			Link.updateUserResourcewithLinks(member.getUser());
			Link.updateRolePerUserOnceResourcewithLinks(member.getUser().getRole(), roles);
		}
		return new ResponseEntity<Iterable<Board>>(board, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Board> getById(@PathVariable("id") String id) {
		Board member = boardRepo.findOne(id);
		if (member != null) {
			Link.updateBoardResourcewithLinks(member);
			Link.updateUserResourcewithLinks(member.getUser());
			Link.updateRoleResourcewithLinks(member.getUser().getRole());
			return new ResponseEntity<Board>(member, HttpStatus.OK);
		} else
			return new ResponseEntity<Board>(member, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{boardId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByBoard(@PathVariable("boardId") String boardId,
			@PathVariable("userId") String userId) {
		User user = userRepo.findById_UserAndId_BoardCaseInsensitive(userId, boardId);
		if (user != null) {
			Link.updateUserPerMemberOfBoardResourcewithLinks(user);
			Link.updateRoleResourcewithLinks(user.getRole());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Board found = boardRepo.findOne(id);
		if (found != null) {
			boardRepo.delete(found);
			return ResponseEntity.ok().body("Member of Board " + id + " successfully deleted");
		} else
			return ResponseEntity.status(204).build();
	}

}
