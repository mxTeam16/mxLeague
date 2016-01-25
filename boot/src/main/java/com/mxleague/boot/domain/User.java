package com.mxleague.boot.domain;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@Column(updatable = false, nullable = false)
	@Size(min = 0, max = 50)
	private String username;

	@Size(min = 0, max = 500)
	private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (!username.equals(user.username))
			return false;

		return true;
	}

}
