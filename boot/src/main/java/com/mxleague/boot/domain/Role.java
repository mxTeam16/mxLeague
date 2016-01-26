package com.mxleague.boot.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "roles", schema="MXLEAGUE")
public class Role {

	@Id
	@Column(name = "id_role", nullable = false)
	@Size(min = 0, max = 20)
	private String id_role;

	@Column(name = "grant")
	@Size(min = 0, max = 10)
	private String grant;

	@OneToMany(mappedBy = "role")
	private List<User> users;

	public Role() {
	}

	public Role(String id_role, String grant) {
		this.id_role = id_role;
		this.grant = grant;
	}

	public String getId_role() {
		return id_role;
	}

	public void setId_role(String id_role) {
		this.id_role = id_role;
	}

	public String getGrant() {
		return grant;
	}

	public void setGrant(String grant) {
		this.grant = grant;
	}

	public void addUser(User user) {
		users.add(user);
		if (user.getRole() != this) {
			user.setRole(this);
		}
	}

	public List<User> getUsers() {
		return users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grant == null) ? 0 : grant.hashCode());
		result = prime * result + ((id_role == null) ? 0 : id_role.hashCode());
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
		Role other = (Role) obj;
		if (grant == null) {
			if (other.grant != null)
				return false;
		} else if (!grant.equals(other.grant))
			return false;
		if (id_role == null) {
			if (other.id_role != null)
				return false;
		} else if (!id_role.equals(other.id_role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id_role=" + id_role + ", grant=" + grant + "]";
	}

}
