package com.mxleague.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", schema = "MXLEAGUE")
public class User {

	@Id
	@Column(name = "id_user", nullable = false)
	@Size(min = 0, max = 20)
	private String id_user;

	@Column(name = "password")
	@Size(min = 0, max = 500)
	private String password;

	@Column(name = "name")
	@Size(min = 0, max = 50)
	private String name;

	@ManyToOne
	@JoinColumn(name = "id_role")
	private Role role;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Player player;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Board board;

	public User() {
	}

	public User(String id_user, String password, String name, Role role) {
		this.id_user = id_user;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlayer(Player player) {
		this.player = player;
		if (player.getUser() != this) {
			player.setUser(this);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setBoard(Board board) {
		this.board = board;
		if (board.getUser() != this) {
			board.setUser(this);
		}
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (id_user == null) {
			if (other.id_user != null)
				return false;
		} else if (!id_user.equals(other.id_user))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id_user=" + id_user + ", password=" + password + ", name=" + name + ", role=" + role + "]";
	}

}
