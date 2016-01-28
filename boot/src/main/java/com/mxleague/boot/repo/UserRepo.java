package com.mxleague.boot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.User;

public interface UserRepo extends CrudRepository<User, String> {
	
	@Query("SELECT u FROM User u WHERE LOWER(u.role) = LOWER(?1)")
	List<User> findById_RoleCaseInsensitive(String id_role);
	
}
