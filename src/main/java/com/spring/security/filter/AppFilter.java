package com.spring.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.service.CustomerDetailsService;
import com.spring.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AppFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	CustomerDetailsService customerDetailsService;
	
	String token=null;
	
	String username=null;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println(">>> Inside AppFilter: " + request.getServletPath());

		
		//first we need to get the  header from the http request
		String header = request.getHeader("Authorization");
		
		System.out.println(header);
		
		
		//once we get the header we will check is it null or present checks prefix contains bearer 
		//and here we will extract token from header
		//username also will be extracted
		if(header!=null&&header.startsWith("Bearer")) {
			token =header.substring(7);
			System.out.println("token "+token);
			try {
			
			username=jwtService.extractUsername(token);
			}
			catch (Exception e) {
				System.out.println("Something going wrong beacuse of "+e.getMessage());
			}
			
			System.out.println("Username "+username);
		}
		
		if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails=customerDetailsService.loadUserByUsername(username);

			try {
			if(jwtService.validateToken(token,userDetails)) {
			

				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}
			}
			catch(Exception e ) {
				System.out.println("Something is going woring beaucse "+e.getMessage());
			}
			
			
		}
		
		filterChain.doFilter(request, response);
		
		
	}

}
