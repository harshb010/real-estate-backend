package com.example.realestate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.realestate.config.JwtProvider;
import com.example.realestate.entity.Agent;

import com.example.realestate.exception.CustomException;
import com.example.realestate.exception.EmailAlreadyExistException;
import com.example.realestate.exception.UserNotFoundException;
import com.example.realestate.repository.AgentRepository;


import jakarta.persistence.EntityNotFoundException;

@Service
public class AgentService {

	 @Autowired
	    private AgentRepository agentRepository;
	    
	    @Autowired
	    private JwtProvider jwtProviderRef;
	    
	    @Autowired
	    private PasswordEncoder encoder;

	    public Agent registerAgent(Agent newAgent) {
	    	Agent agent = agentRepository.findByEmail(newAgent.getEmail());
	    	if(agent == null)
	    	{
	    		return agentRepository.save(newAgent);
	    	}
	    	else {
	    		throw new EmailAlreadyExistException("An account with this Email already exists");
	    	}
	        
	    }

	    public Agent findByName(String name) {
	        Agent agent = agentRepository.findByName(name);
	        if (agent == null) {
	            throw new UsernameNotFoundException("Agent not found with name: " + name);
	        }
	        return agent;
	    }

	    public Agent findById(Long id) {
	        return agentRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
	    }


	    public List<Agent> findAll() {
	        List<Agent> agentList = agentRepository.findAll();
	        if(agentList.size()!=0)
	        	return agentList;
	        else {
	        	throw new UserNotFoundException("Agent Not Found");
	        }
	    }
	    
	    public ResponseEntity<?> deleteAgent(Long id) {
	       Agent agent = agentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Agent Not Found"));
	       if(agent != null)
	       {
	    	   agentRepository.delete(agent);
	    	   return new ResponseEntity<>("Agent Deleted",HttpStatus.OK);   
	       }else {
	    	   throw new CustomException("Unable to Delete");
	       }
	    }

	    public ResponseEntity<?> updateAgent(Agent agentRef) {
			Agent agent = agentRepository.findById(agentRef.getId()).orElseThrow(() -> new UserNotFoundException("user Not Found to Update"));
			if (agent != null) {
				agent.setName(agentRef.getName());
				agent.setEmail(agentRef.getEmail());
				agent.setContactInfo(agentRef.getContactInfo());
				agent.setPassword(agentRef.getPassword());
				agent.setAppointments(agentRef.getAppointments());
				agentRepository.save(agent);
				return new ResponseEntity<>("info Updated", HttpStatus.OK);
			} else {
				throw new CustomException("unable to update");
			}
		}

		public ResponseEntity<?> upadatePassword(String email, String oldPassword,String newPassword) {
			Agent agent = agentRepository.findByEmail(email);
			System.out.println(email+","+oldPassword+","+newPassword);
			if (agent != null) {
				String	oldPass=agent.getPassword();
				
				if (!encoder.matches(oldPass, agent.getPassword())) {
					agent.setPassword(encoder.encode(newPassword));
					agentRepository.save(agent);
				return new ResponseEntity<>("Password change successuful", HttpStatus.OK);
				}else {
					throw new CustomException("unable to update password");
				}
			} else {
				throw new CustomException("unable to change password");
			}
		}
		
			public UserDetails loadAgentByname(String name) throws UserNotFoundException {
				Agent agent = agentRepository.findByEmail(name);
				if (agent == null) {
					throw new UserNotFoundException("Agent not found with email: " + name);
				}
				List<GrantedAuthority> authorities = new ArrayList<>();
				return new org.springframework.security.core.userdetails.User(agent.getEmail(), agent.getPassword(), authorities);
			}

			
			public Agent findAgentProfileByJwt(String jwt) throws UserNotFoundException {
				String email = jwtProviderRef.getEmailFromToken(jwt);
				Agent agent = agentRepository.findByEmail(email);
				if (agent == null) {
					throw new UserNotFoundException("Agent not found with this email: " + email);
				}
				return agent;
		}

			public Agent GetByEmail(String mail) {
				Agent agentRef = agentRepository.findByEmail(mail);
				if (agentRef != null) {
					return agentRef;
				} else {

					throw new UserNotFoundException("Agent Not Found");

				}
			}
			
}
