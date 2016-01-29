package com.mxleague.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public RoleHierarchy roleHierarchy() {
	    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
	    roleHierarchy.setHierarchy("R/W/X > R/W and R/W > R");
	    return roleHierarchy;
	}
	
	private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
	    DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
	    defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
	    return defaultWebSecurityExpressionHandler;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
		  .csrf().disable()
		  .formLogin()
		     .permitAll()
		     .and()
	      .authorizeRequests()
	         .expressionHandler(webExpressionHandler())
	         .antMatchers("/rest/**")
	         .permitAll()
	         .antMatchers("**")
             .hasAuthority("R");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
