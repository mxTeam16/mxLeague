package com.mxleague.boot.repo;

import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.Player;

public interface TransferRepo extends CrudRepository<Player, String> {

}
