package com.mxleague.client;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mxleague.client.services.PanelService;
import com.mxleague.domain.Player;
import com.mxleague.domain.Statistic;
import com.mxleague.domain.Transfer;
import com.mxleague.domain.User;

@Component("stats")
@Scope("session")
public class UserStatBean implements Serializable {

	@Autowired
	PanelService service;

	private User user;
	private List<Statistic> data;
	private List<Transfer> dataTransfer;
	private List<Player> dataP;
	private Statistic modifiedStat;
	
	private String username;
	private String pass;
	
	private static final long serialVersionUID = 1L;

	public String login(String username, String pass) {
		user = service.getLoginUser(username, pass);
		
		this.username = username;
		this.pass = pass;

		if (user != null) {
			dataP = service.getPlayers(username, pass);
			service.linkPlayer(user, dataP);
			data = service.getStatistics(username, pass);
			service.linkStats(user, data);
			if(user.getRole().getId_role().equals("admin")){
				
				dataTransfer = service.getTransfers(username, pass);
				service.linkTransfer(user, dataTransfer);
			}
			return "success";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - Login Failed", ""));
			return "failure";
		}
	}
	
	public String logout(){
		user = null;
		data = null;
		dataTransfer = null;
		modifiedStat = null;
		return "userPanel.xhtml";
	}
	
	public void modify(Statistic s){
		modifiedStat = s;
	}
	
	public void save(){
		service.saveStatistic(username, pass, modifiedStat);
		modifiedStat = null;
	}

	public Statistic getModifiedStat() {
		return modifiedStat;
	}

	public void setModifiedStat(Statistic modifiedStat) {
		this.modifiedStat = modifiedStat;
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

	public List<Transfer> getDataTransfer() {
		return dataTransfer;
	}

	public void setDataTransfer(List<Transfer> dataTransfer) {
		this.dataTransfer = dataTransfer;
	}
	
	
}
