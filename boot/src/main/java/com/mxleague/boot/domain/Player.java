package com.mxleague.boot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "players")
public class Player {

	@Id
	@Column(name = "id_player", nullable = false)
	@Size(min = 0, max = 20)
	private String id_player;

	@Column(name = "position")
	@Size(min = 0, max = 500)
	private String position;

	@Column(name = "status")
	@Size(min = 0, max = 50)
	private String status;

	@OneToOne
	@JoinColumn(name = "id_user")
	private User user;

	@OneToOne(mappedBy = "player")
	private Statistic statistic;
	
	@OneToOne(mappedBy = "player")
	private Transfer transfer;

	public String getId_player() {
		return id_player;
	}

	public void setId_player(String id_player) {
		this.id_player = id_player;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
		if (statistic.getPlayer() != this) {
			statistic.setPlayer(this);
		}
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
		if (transfer.getPlayer() != this) {
			transfer.setPlayer(this);
		}
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public Player() {
	}

	public Player(String id_player, String position, String status, User user) {
		this.id_player = id_player;
		this.position = position;
		this.status = status;
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_player == null) ? 0 : id_player.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Player other = (Player) obj;
		if (id_player == null) {
			if (other.id_player != null)
				return false;
		} else if (!id_player.equals(other.id_player))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [id_player=" + id_player + ", position=" + position + ", status=" + status + ", user=" + user
				+ "]";
	}
}
