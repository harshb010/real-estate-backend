package com.example.realestate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.realestate.config.JwtProvider;
import com.example.realestate.entity.Agent;
import com.example.realestate.entity.Builder;
import com.example.realestate.entity.User;
import com.example.realestate.exception.EmailAlreadyExistException;
import com.example.realestate.exception.UserNotFoundException;
import com.example.realestate.repository.AgentRepository;
import com.example.realestate.repository.BuilderRepository;
import com.example.realestate.repository.UserRepository;
import com.example.realestate.request.LoginRequest;
import com.example.realestate.response.AuthResponse;
import com.example.realestate.service.AgentService;
import com.example.realestate.service.BuilderService;
import com.example.realestate.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepoRef;
	private AgentRepository agentRepoRef;
	private BuilderRepository builderRepoRef;
	private JwtProvider jwtProvider;
	private PasswordEncoder encoder;
	private UserService userServiceRef;
	private  AgentService agentServiceRef;
	private BuilderService builderServiceRef;

	public AuthController(UserRepository userRepoRef, AgentRepository agentRepoRef, BuilderRepository builderRepoRef, JwtProvider jwtProvider, PasswordEncoder encoder,
			UserService userServiceRef, AgentService agentServiceRef, BuilderService builderServiceRef) {
		this.userRepoRef = userRepoRef;
		this.agentRepoRef = agentRepoRef;
		this.builderRepoRef = builderRepoRef;
		this.jwtProvider = jwtProvider;
		this.encoder = encoder;
		this.userServiceRef = userServiceRef;
		this.agentServiceRef = agentServiceRef;
		this.builderServiceRef = builderServiceRef;
	}

	@PostMapping("/userregister")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws EmailAlreadyExistException {
		String email = user.getEmail();
		String pass = user.getPassword();
		String firstName = user.getFirstName();
		
		

		User isEmailExist = userRepoRef.findByEmail(email);
		if (isEmailExist != null) {
			throw new EmailAlreadyExistException("Email is already exist");
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(encoder.encode(pass));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(user.getLastName());
		createdUser.setAddress(user.getAddress());
		createdUser.setPhoneNumber(user.getPhoneNumber());
		createdUser.setRole(user.getRole());
		
	

		User savedUser = userRepoRef.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new ResponseEntity<AuthResponse>(new AuthResponse(token, "Signup success"), HttpStatus.CREATED);
	}

	@PostMapping("/userlogin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
		String userName = loginRequest.getEmail();
		String pass = loginRequest.getPassword();

		UserDetails userDetails = userServiceRef.loadUserByUsername(userName);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username !!");
		}
		if (!encoder.matches(pass, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password !!");
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new ResponseEntity<AuthResponse>(new AuthResponse(token, "Login success"), HttpStatus.OK);
	}
	
	@GetMapping("/getuserbyjwt")
	public ResponseEntity<?> getuserByJWTToken(@RequestHeader("Authorization") String token){
		try {
			return new ResponseEntity<User>(userServiceRef.findUserProfileByJwt(token),HttpStatus.OK);
		}catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/agentregister")
	public ResponseEntity<AuthResponse> createAgentHandler(@RequestBody Agent agent) throws EmailAlreadyExistException {
		String email = agent.getEmail();
		String pass = agent.getPassword();
		String name = agent.getName();
		
		

		Agent isEmailExist = agentRepoRef.findByEmail(email);
		if (isEmailExist != null) {
			throw new EmailAlreadyExistException("Email is already exist");
		}
		Agent createdAdmin = new Agent();
		createdAdmin.setEmail(email);
		createdAdmin.setPassword(encoder.encode(pass));
		createdAdmin.setName(name);
		createdAdmin.setContactInfo(agent.getContactInfo());
	
		Agent savedAgent = agentRepoRef.save(createdAdmin);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedAgent.getEmail(),
				savedAgent.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new ResponseEntity<AuthResponse>(new AuthResponse(token, "Signup success"), HttpStatus.CREATED);
	}

	@PostMapping("/agentlogin")
	public ResponseEntity<AuthResponse> loginAgentHandler(@RequestBody LoginRequest loginRequest) {
		String userName = loginRequest.getEmail();
		String pass = loginRequest.getPassword();

		UserDetails agentDetails = agentServiceRef.loadAgentByname(userName);
		if (agentDetails == null) {
			throw new BadCredentialsException("Invalid Username !!");
		}
		if (!encoder.matches(pass, agentDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password !!");
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(agentDetails, null,
				agentDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new ResponseEntity<AuthResponse>(new AuthResponse(token, "Login success"), HttpStatus.OK);
	}
	
	@GetMapping("/getagentbyjwt")
	public ResponseEntity<?> getagentByJWTToken(@RequestHeader("Authorization") String token){
		try {
			return new ResponseEntity<Agent>(agentServiceRef.findAgentProfileByJwt(token),HttpStatus.OK);
		}catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	@PostMapping("/builderregister")
	public ResponseEntity<AuthResponse> createBuilderHandler(@RequestBody Builder builder) throws EmailAlreadyExistException {
		String email = builder.getEmail();
		String pass = builder.getPassword();
		String name = builder.getName();
		String companyName = builder.getCompanyName();
		String contactInfo = builder.getContactInfo();
		

		Builder isEmailExist = builderRepoRef.findByEmail(email);
		if (isEmailExist != null) {
			throw new EmailAlreadyExistException("Email is already exist");
		}
		Builder createdBuilder = new Builder();
		createdBuilder.setEmail(email);
		createdBuilder.setPassword(encoder.encode(pass));
		createdBuilder.setName(name);
		createdBuilder.setCompanyName(companyName);
		createdBuilder.setContactInfo(contactInfo);
	
		Builder savedBuilder = builderRepoRef.save(createdBuilder);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedBuilder.getEmail(),
				savedBuilder.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new ResponseEntity<AuthResponse>(new AuthResponse(token, "Signup success"), HttpStatus.CREATED);
	}

	@PostMapping("/builderlogin")
	public ResponseEntity<AuthResponse> loginBuilderHandler(@RequestBody LoginRequest loginRequest) {
		String userName = loginRequest.getEmail();
		String pass = loginRequest.getPassword();

		UserDetails builderDetails = builderServiceRef.loadBuilderByname(userName);
		if (builderDetails == null) {
			throw new BadCredentialsException("Invalid Username !!");
		}
		if (!encoder.matches(pass, builderDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password !!");
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(builderDetails, null,
				builderDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new ResponseEntity<AuthResponse>(new AuthResponse(token, "Login success"), HttpStatus.OK);
	}
	
	@GetMapping("/getbuilderbyjwt")
	public ResponseEntity<?> getbuilderByJWTToken(@RequestHeader("Authorization") String token){
		try {
			return new ResponseEntity<Builder>(builderServiceRef.findBuilderProfileByJwt(token),HttpStatus.OK);
		}catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	
}










