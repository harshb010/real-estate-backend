package com.example.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.realestate.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent findByName(String name);
	   // User findById(long id);
	    
	    Agent findByEmail(String email);

		//List<Agent> findByRole(String role);
}
