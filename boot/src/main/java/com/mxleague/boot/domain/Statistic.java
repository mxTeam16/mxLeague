package com.mxleague.boot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "statistics")
public class Statistic {

	@Id
	@Column(name = "id_statistic", nullable = false)
	@Size(min = 0, max = 20)
	private String id_statistic;

	@Column(name = "score")
	private int score;

	@Column(name = "matchesplayed")
	private int matchesplayed;

	@Column(name = "goals")
	private int goals;

	@OneToOne
	@JoinColumn(name = "id_player")
	@Size(min = 0, max = 20)
	private Player player;

	public String getId_statistic() {
		return id_statistic;
	}

	public void setId_statistic(String id_statistic) {
		this.id_statistic = id_statistic;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMatchesplayed() {
		return matchesplayed;
	}

	public void setMatchesplayed(int matchesplayed) {
		this.matchesplayed = matchesplayed;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Statistic() {
	}

	public Statistic(String id_statistic, int score, int matchesplayed, int goals, Player player) {
		this.id_statistic = id_statistic;
		this.score = score;
		this.matchesplayed = matchesplayed;
		this.goals = goals;
		this.player = player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + goals;
		result = prime * result + ((id_statistic == null) ? 0 : id_statistic.hashCode());
		result = prime * result + matchesplayed;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistic other = (Statistic) obj;
		if (goals != other.goals)
			return false;
		if (id_statistic == null) {
			if (other.id_statistic != null)
				return false;
		} else if (!id_statistic.equals(other.id_statistic))
			return false;
		if (matchesplayed != other.matchesplayed)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (score != other.score)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Statistic [id_statistic=" + id_statistic + ", score=" + score + ", matchesplayed=" + matchesplayed
				+ ", goals=" + goals + ", player=" + player + "]";
	}
}
