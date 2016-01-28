package com.mxleague.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableEurekaClient
@Controller
public class TestClient {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
    @RequestMapping("/first")
    public String home() {
    	InstanceInfo instance = discoveryClient.getNextServerFromEureka("first-service", false);
        return instance.getHomePageUrl();
    }

    public static void main(String[] args) {
    	System.setProperty("spring.config.name", "test-client");
        new SpringApplicationBuilder(TestClient.class).web(true).run(args);
    }

}