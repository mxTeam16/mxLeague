package com.mxleague.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mxleague.domain.Statistic;
import com.mxleague.domain.User;

@Service

public class PanelService {
	
	private static final Logger LOGGER = Logger.getLogger(PanelService.class.getName());
	
	@Autowired
	LoadBalancerClient client;
	
	public User getLoginUser(String user, String pass) {
		ServiceInstance instance = client.choose("first-service");
		HttpResponse loginResponse = doLogin(user,pass);
		int statusCode = loginResponse.getStatusLine().getStatusCode();
		User userObject = null;
		LOGGER.info("Status Code: " +statusCode);
		if (statusCode == 200) {
			RestTemplate template = new BasicAuthRestTemplate(user, pass);
			userObject = template.getForObject(instance.getUri() + "rest/v1/users/"+user, User.class);
		} else {
			LOGGER.warning("Login failed:" +loginResponse.getStatusLine().toString());
		}

		return userObject;
	}

	private HttpResponse doLogin(String user, String pass) {
		LOGGER.info("Login Credentials: " +user+":"+pass);
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

	public List<Statistic> getStatistics(String user,String pass) {
		List<Statistic> statList = null;
		ServiceInstance instance = client.choose("first-service");
		RestTemplate template = new BasicAuthRestTemplate(user, pass);
		statList = template.getForObject(instance.getUri() + "/rest/v1/statistics", List.class);
		return statList;
	}

}
