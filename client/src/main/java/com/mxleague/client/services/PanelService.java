package com.mxleague.client.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mxleague.domain.Player;
import com.mxleague.domain.Statistic;
import com.mxleague.domain.Transfer;
import com.mxleague.domain.User;

@Service

public class PanelService {

	private static final Logger LOGGER = Logger.getLogger(PanelService.class.getName());

	@Autowired
	LoadBalancerClient client;

	private HttpResponse doLogin(String user, String pass) {
		LOGGER.info("Login Credentials: " + user + ":" + pass);
		ServiceInstance instance = client.choose("first-service");
		String userpass = user + ":" + pass;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String encoding = Base64.encodeBase64String(userpass.getBytes());
		HttpGet httpget = new HttpGet(instance.getUri() + "/rest/v1/users");
		httpget.setHeader("Authorization", "Basic " + encoding);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}	

	public User getLoginUser(String user, String pass) {
		ServiceInstance instance = client.choose("first-service");
		HttpResponse loginResponse = doLogin(user, pass);
		int statusCode = loginResponse.getStatusLine().getStatusCode();
		User userObject = null;
		LOGGER.info("Status Code: " + statusCode);
		if (statusCode == 200) {
			RestTemplate template = new BasicAuthRestTemplate(user, pass);
			userObject = template.getForObject(instance.getUri() + "rest/v1/users/" + user, User.class);
		} else {
			LOGGER.warning("Login failed:" + loginResponse.getStatusLine().toString());
		}

		return userObject;
	}
	
	public List<Player> getPlayers(String user, String pass) {
		List<Player> playerList = null;
		ServiceInstance instance = client.choose("first-service");
		RestTemplate template = new BasicAuthRestTemplate(user, pass);
		Player[] data = template.getForObject(instance.getUri() + "/rest/v1/players", Player[].class);
		playerList = Arrays.asList(data);
		return playerList;
	}

	public List<Statistic> getStatistics(String user, String pass) {
		List<Statistic> statList = null;
		ServiceInstance instance = client.choose("first-service");
		RestTemplate template = new BasicAuthRestTemplate(user, pass);
		Statistic[] data = template.getForObject(instance.getUri() + "/rest/v1/statistics", Statistic[].class);
		statList = Arrays.asList(data);
		return statList;
	}
	
	public List<Transfer> getTransfers(String user, String pass) {
		List<Transfer> transferList = null;
		ServiceInstance instance = client.choose("first-service");
		RestTemplate template = new BasicAuthRestTemplate(user, pass);
		Transfer[] data = template.getForObject(instance.getUri() + "/rest/v1/transfers", Transfer[].class);
		transferList = Arrays.asList(data);
		return transferList;
	}
	
	public void linkPlayer(User u,List<Player> data){
		for (Player p : data) {
			if(p.getUser().getId_user().equals(u.getId_user())){
				u.setPlayer(p);
			}
		}
	}
	
	public void linkStats(User u,List<Statistic> data){
		for (Statistic s : data) {
			if(s.getPlayer().getUser().getId_user().equals(u.getId_user())){
				u.setPlayer(s.getPlayer());
			}
		}
	}
	
	public void linkTransfer(User u,List<Transfer> data){
		for (Transfer t : data) {
			if(t.getPlayer().getUser().getId_user().equals(u.getId_user())){
				u.setPlayer(t.getPlayer());
			}
		}
	}
	
	public void saveStatistic(String user, String pass, Statistic s){
		ServiceInstance instance = client.choose("first-service");
		RestTemplate template = new BasicAuthRestTemplate(user, pass);
		try {
			template.put(instance.getUri() + "/rest/v1/statistics/" + s.getId_statistic(), s);
		} catch(HttpClientErrorException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - Permision denied, user must be admin", ""));
		}
	}
	

}
