package com.mxleague.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FirstService 
{
    public static void main( String[] args )
    {
     System.setProperty("spring.config.name", "first-service");
      SpringApplication.run(FirstService.class, args);
    }
}
