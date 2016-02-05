package com.mxleague.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "transfers", schema = "MXLEAGUE")
public class Transfer {

	@Id
	@Column(name = "id_transfer", nullable = false)
	@Size(min = 0, max = 20)
	private String id_transfer;

	@Column(name = "amount")
	private int amount;

	@Column(name = "teamfrom")
	@Size(min = 0, max = 20)
	private String teamfrom;

	@Column(name = "teamto")
	@Size(min = 0, max = 20)
	private String teamto;

	@OneToOne
	@JoinColumn(name = "id_player")
	private Player player;

	public String getId_transfer() {
		return id_transfer;
	}

	public void setId_transfer(String id_transfer) {
		this.id_transfer = id_transfer;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTeamfrom() {
		return teamfrom;
	}

	public void setTeamfrom(String teamfrom) {
		this.teamfrom = teamfrom;
	}

	public String getTeamto() {
		return teamto;
	}

	public void setTeamto(String teamto) {
		this.teamto = teamto;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Transfer() {
	}

	public Transfer(String id_transfer, int amount, String teamfrom, String teamto, Player player) {
		this.id_transfer = id_transfer;
		this.amount = amount;
		this.teamfrom = teamfrom;
		this.teamto = teamto;
		this.player = player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((id_transfer == null) ? 0 : id_transfer.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((teamfrom == null) ? 0 : teamfrom.hashCode());
		result = prime * result + ((teamto == null) ? 0 : teamto.hashCode());
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
		Transfer other = (Transfer) obj;
		if (amount != other.amount)
			return false;
		if (id_transfer == null) {
			if (other.id_transfer != null)
				return false;
		} else if (!id_transfer.equals(other.id_transfer))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (teamfrom == null) {
			if (other.teamfrom != null)
				return false;
		} else if (!teamfrom.equals(other.teamfrom))
			return false;
		if (teamto == null) {
			if (other.teamto != null)
				return false;
		} else if (!teamto.equals(other.teamto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transfer [id_transfer=" + id_transfer + ", amount=" + amount + ", teamfrom=" + teamfrom + ", teamto="
				+ teamto + ", player=" + player + "]";
	}
}
