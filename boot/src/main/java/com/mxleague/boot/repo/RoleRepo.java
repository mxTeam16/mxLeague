package com.mxleague.boot.repo;

import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.Role;

public interface RoleRepo extends CrudRepository<Role, String> {
	
} 
