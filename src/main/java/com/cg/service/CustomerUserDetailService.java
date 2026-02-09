package com.cg.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.entity.User;
import com.cg.repository.UserRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService{
	
	@Autowired
    private UserRepository userRepository;

	
	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        // 1. Fetch user from DB by email
	        User user = userRepository.findByEmail(username) 
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

	        // 2. Use the User Builder (Cleanest and Safest way)
	        return org.springframework.security.core.userdetails.User.builder()
	                .username(user.getEmail())
	                .password(user.getPassword())
	                .authorities("ROLE_" + user.getRole()) // Explicitly set authority
	                .build();

	    }


}
