package com.example.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.realestate.entity.Builder;

@Repository
public interface BuilderRepository extends JpaRepository<Builder, Long> {
   
	Builder findByName(String name);
	   // User findById(long id);
	    
	    Builder findByEmail(String email);

		
}