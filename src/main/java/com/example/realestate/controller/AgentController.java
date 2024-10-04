package com.example.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.realestate.entity.Agent;

import com.example.realestate.exception.CustomException;
import com.example.realestate.exception.UserNotFoundException;
import com.example.realestate.request.UpdatePass;
import com.example.realestate.service.AgentService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/register")
    public ResponseEntity<Agent> registerAgent(@RequestBody Agent agent) {
        Agent newAgent = agentService.registerAgent(agent);
        return ResponseEntity.ok(newAgent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable Long id) {
        Agent agent = agentService.findById(id);
        return ResponseEntity.ok(agent);
    }

    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentService.findAll();
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/getagentbyemail")
	public ResponseEntity<?> getAgentByEmail(@RequestHeader("Authorization") String token) {
		try {
			Agent agent = agentService.findAgentProfileByJwt(token);
			String email = agent.getEmail();
			return new ResponseEntity<>(agentService.GetByEmail(email), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
    @PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Agent agentRef) {
		try {
			return agentService.updateAgent(agentRef);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
    @DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAgent(@PathVariable Long id) {
		try {
			return agentService.deleteAgent(id);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
    
    @PutMapping("/updatepassword")
	public ResponseEntity<?> updatePasword1(@RequestBody UpdatePass updatePassRef) {
		// System.out.println(userId + "," + password);
		try {
			return new ResponseEntity<>(agentService.upadatePassword(updatePassRef.getEmail(),
					updatePassRef.getOldPassword(), updatePassRef.getNewPassword()), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }
}
	