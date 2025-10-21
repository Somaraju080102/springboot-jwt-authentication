package com.spring.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.security.filter.AppFilter;
import com.spring.security.service.CustomerDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	AppFilter appFilter;
	
	@Autowired
	CustomerDetailsService customerDetailsService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// here we use passeord encoder to encrypt our passowrd before saving to db 
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(customerDetailsService); //when we pass serivice class autmatically loadby method calls and return the customer object
		
		provider.setPasswordEncoder(passwordEncoder()); //customer object has a password feild that will be encrypted using bycrypt encoder
		 
		return provider;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http
			    .csrf(csrf -> csrf.disable())
			    .authorizeHttpRequests(auth -> auth
			        .requestMatchers("/api/login", "/api/register").permitAll()
			        .requestMatchers("/*").authenticated()
			    )
			    .sessionManagement(session -> session
			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			    )
			    .authenticationProvider(authenticationProvider())
			    .addFilterBefore(appFilter, UsernamePasswordAuthenticationFilter.class)
			    .build();

		
		
	}

}
