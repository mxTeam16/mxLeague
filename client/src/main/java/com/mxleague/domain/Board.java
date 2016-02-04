package com.mxleague.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "board", schema = "MXLEAGUE")
public class Board {

	@Id
	@Column(name = "id_employee", nullable = false)
	@Size(min = 0, max = 20)
	private String id_employee;

	@Column(name = "job")
	private String job;

	@OneToOne
	@JoinColumn(name = "id_user")
	private User user;

	public String getId_board() {
		return id_employee;
	}

	public void setId_board(String id_employee) {
		this.id_employee = id_employee;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Board() {
	}

	public Board(String id_employee, String job, User user) {
		this.id_employee = id_employee;
		this.job = job;
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_employee == null) ? 0 : id_employee.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
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
		Board other = (Board) obj;
		if (id_employee == null) {
			if (other.id_employee != null)
				return false;
		} else if (!id_employee.equals(other.id_employee))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
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
		return "Board [id_employee=" + id_employee + ", job=" + job + ", user=" + user + "]";
	}
}
