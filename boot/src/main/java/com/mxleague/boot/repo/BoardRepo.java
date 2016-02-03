package com.mxleague.boot.repo;

import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.Board;

public interface BoardRepo extends CrudRepository<Board, String> {

}
