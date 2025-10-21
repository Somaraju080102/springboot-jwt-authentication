package com.spring.security.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.entity.Customer;
import com.spring.security.repo.CustomerRepo;

@Service
public class CustomerDetailsService implements UserDetailsService{
	
	//Here we need to implement UserDetailsService which is provided by spring security for authentication.
	
	@Autowired
	CustomerRepo customerRepo;

	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		// By default when we implemented the UserDetailsService interface  we need to override one method 
		
//		Customer byName = customerRepo.findByName("varma");
		
		Customer byName = customerRepo.findByName(name);
		
		System.out.println(byName.getName());

		
		
		return new User(byName.getName(), byName.getPassword(), Collections.emptyList());
		
	}
	
	

}
