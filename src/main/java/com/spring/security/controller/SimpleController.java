package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.entity.Customer;
import com.spring.security.repo.CustomerRepo;
import com.spring.security.service.JwtService;

@RestController("")
public class SimpleController {
	
	@Autowired
	CustomerRepo  customerRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;

	
	@GetMapping("/greet")
	public String getMsg() {
		return "Welcome to Spring Security";
	}
	
	
	@PostMapping("/api/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
		
		String encode = passwordEncoder.encode(customer.getPassword());
		
		customer.setPassword(encode);
		
		customerRepo.save(customer);
		
		return new  ResponseEntity<String>("Registration Suceessful",HttpStatus.OK);
				
			
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<String> loginCustomer(@RequestBody Customer customer) {
		
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(customer.getName(), customer.getPassword());
		
		
		try {
			Authentication authenticate = authenticationManager.authenticate(token);
			
			if(authenticate.isAuthenticated()) {
				
				String jwtToken  = jwtService.generateToken(customer.getName());
				System.out.println("JWT token "+jwtToken);
				return new ResponseEntity<String>(jwtToken,HttpStatus.CREATED);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Invalid Credentials",HttpStatus.UNAUTHORIZED);
	}
	
	

}
