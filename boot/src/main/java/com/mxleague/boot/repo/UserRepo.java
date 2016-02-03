package com.mxleague.boot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.User;

public interface UserRepo extends CrudRepository<User, String> {

	@Query("SELECT u FROM User u WHERE LOWER(u.role) = LOWER(?1)")
	List<User> findById_RoleCaseInsensitive(String id_role);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.id_user) = LOWER(?1) AND LOWER(u.role) = LOWER(?2)")
	User findById_UserAndId_RoleCaseInsensitive(String id_user, String id_role);

	@Query("SELECT u FROM User u WHERE LOWER(u.id_user) = LOWER(?1) AND LOWER(u.player) = LOWER(?2)")
	User findById_UserAndId_PlayerCaseInsensitive(String id_user, String id_player);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.id_user) = LOWER(?1) AND LOWER(u.board) = LOWER(?2)")
	User findById_UserAndId_BoardCaseInsensitive(String id_user, String id_board);

}
