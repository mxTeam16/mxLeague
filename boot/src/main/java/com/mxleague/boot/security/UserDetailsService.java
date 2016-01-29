package com.mxleague.boot.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mxleague.boot.domain.User;
import com.mxleague.boot.repo.UserRepo;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepo userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {

		String lowercaseLogin = login.toLowerCase();

		User userFromDatabase = userRepository.findOne(lowercaseLogin);

		if (userFromDatabase == null) {
			throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
		}

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userFromDatabase.getRole().getGrant());
		grantedAuthorities.add(grantedAuthority);

		return new org.springframework.security.core.userdetails.User(userFromDatabase.getId_user(),
				userFromDatabase.getPassword(), grantedAuthorities);

	}

}
