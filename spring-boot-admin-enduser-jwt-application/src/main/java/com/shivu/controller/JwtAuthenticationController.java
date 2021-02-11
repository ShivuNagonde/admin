package com.shivu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shivu.config.JwtTokenUtil;
import com.shivu.model.JwtRequest;
import com.shivu.model.MessageResponse;
import com.shivu.model.User;
import com.shivu.model.UserDto;
import com.shivu.repository.UserRepository;
import com.shivu.service.JwtUserDetailsService;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
    @Autowired
	private UserRepository userRepo;
    @Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object createLoginToken(@RequestBody JwtRequest authenticationRequest,HttpSession session, HttpServletRequest request ) throws Exception {
		Map<String, Object> map1=new HashMap<>();
		Map<String, Object> map2=new HashMap<>();
        authenticate(authenticationRequest.getEmailId(), authenticationRequest.getPassword());
         final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmailId());
         final String token = jwtTokenUtil.generateToken(userDetails);
         User u=userRepo.findByEmailId(authenticationRequest.getEmailId());
         if(u != null) {
         map1.put("token1", token);
         map1.put("username", u.getName());
         map1.put("password", u.getPassword());
         map1.put("email", u.getEmailId());
         map1.put("Well Come", u.getRole()+" Login Successfully.");
         if(u.getRole().equals("ADMIN")) {
             String A_SESSION  = (String) request.getSession().getAttribute("ADMIN_SESSION");
             request.getSession().setAttribute("ADMIN_SESSION",token);
         }else if(u.getRole().equals("ENDUSER")) {
             String E_SESSION  = (String) request.getSession().getAttribute("ENDUSER_SESSION");
             request.getSession().setAttribute("ENDUSER_SESSION",token);}
		return map1;
		}
         return null;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> userRegister(@RequestBody UserDto user) throws Exception {
		
		if(userRepo.existsByEmailId(user.getEmailId())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Email is already exist!"));
		}
		if(userRepo.existsByPassword(user.getPassword())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Password is already use!"));
		}
		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setRole(user.getRole().toUpperCase());
		newUser.setEmailId(user.getEmailId());;
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setPhoneNo(user.getPhoneNo());
		newUser.setCreatedOn(new Date());
		userRepo.save(newUser);
		return ResponseEntity.ok(new MessageResponse(user.getRole()+" registered successfully!"));	    
	}
	private void authenticate(String emailId, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}