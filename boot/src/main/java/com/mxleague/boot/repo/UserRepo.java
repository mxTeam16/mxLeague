package com.mxleague.boot.repo;

import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.User;

public interface UserRepo extends CrudRepository<User, String> {
	
}
