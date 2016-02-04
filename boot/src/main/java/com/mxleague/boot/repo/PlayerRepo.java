package com.mxleague.boot.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mxleague.boot.domain.Player;

public interface PlayerRepo extends CrudRepository<Player, String> {

	@Query("SELECT p FROM Player p WHERE LOWER(p.id_player) = LOWER(?1) AND LOWER(p.statistic.id_statistic) = LOWER(?2)")
	Player findById_PlayerAndId_StatisticCaseInsensitive(String id_player, String id_statistic);
	
	@Query("SELECT p FROM Player p WHERE LOWER(p.id_player) = LOWER(?1) AND LOWER(p.transfer.id_transfer) = LOWER(?2)")
	Player findById_PlayerAndId_TransferCaseInsensitive(String id_player, String id_trasnfer);
}
