package com.spring.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.entity.Customer;
import java.util.List;


public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
//	 Customer findByEmail(String email);
	 
	 Customer findByName(String name);

}
