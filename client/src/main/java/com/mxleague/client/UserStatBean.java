package com.mxleague.client;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mxleague.domain.Statistic;
import com.mxleague.domain.User;


@Component("stats")
@Scope("session")
public class UserStatBean implements Serializable {

	@Autowired
	PanelService service;
	
	private User user;
	private List<Statistic> data;

	private static final long serialVersionUID = 1L;
	
	public String login(String username, String pass) {	
		user = service.getLoginUser(username, pass);
		//data = service.getStatistics(username,pass);
		if(user!=null){
			return "success";
		}else{
			FacesContext.getCurrentInstance().
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - Login Failed", ""));
			return "failure";
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Statistic> getData() {
		return data;
	}

	public void setData(List<Statistic> data) {
		this.data = data;
	}
	
	

}
