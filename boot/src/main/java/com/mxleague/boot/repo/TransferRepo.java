package com.mxleague.boot.repo;

import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.Transfer;

public interface TransferRepo extends CrudRepository<Transfer, String> {

}
