package com.shivu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shivu.model.User;
import com.shivu.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	//@Autowired
	//private EndUserRepository endUserrepo;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		User u = userRepo.findByEmailId(emailId);
		//EndUser user = endUserrepo.findByEmailId(emailId);
		if (u == null) {
			throw new UsernameNotFoundException("User not found with userEmailId: " + emailId);
			
		}
		return new org.springframework.security.core.userdetails.User(u.getEmailId(), u.getPassword(),
				new ArrayList<>());
		/*
			 * else if (user != null) { //throw new
			 * UsernameNotFoundException("Admin not found with adminEmailId: " + emailId);
			 * return new
			 * org.springframework.security.core.userdetails.User(user.getEmailId(),
			 * user.getPassword(), new ArrayList<>()); }
			 */
		//return null;
		
	}
	
}