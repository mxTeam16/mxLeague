package com.mxleague.boot.repo;

import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.Statistic;

public interface StatisticRepo extends CrudRepository<Statistic, String> {

}
