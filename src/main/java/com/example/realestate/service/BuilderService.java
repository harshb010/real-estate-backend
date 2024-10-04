package com.example.realestate.service;

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
import com.example.realestate.entity.Builder;
import com.example.realestate.entity.User;
import com.example.realestate.exception.CustomException;
import com.example.realestate.exception.UserNotFoundException;
import com.example.realestate.repository.BuilderRepository;
import com.example.realestate.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuilderService {

	 @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private JwtProvider jwtProviderRef;
	    
	    @Autowired
	    private PasswordEncoder encoder;
	
    @Autowired
    private BuilderRepository builderRepository;

    public Builder addBuilder(Builder builder) {
        return builderRepository.save(builder);
    }

    public Builder findById(Long id) {
        return builderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Builder not found with id: " + id));
    }

    public List<Builder> findAll() {
        return builderRepository.findAll();
    }

    public ResponseEntity<?> deleteBuilder(Long id) {
        Builder builder = builderRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if(builder != null)
        {
     	   builderRepository.delete(builder);
     	   return new ResponseEntity<>("User Deleted",HttpStatus.OK);   
        }else {
     	   throw new CustomException("Unable to Delete");
        }
     }

     public ResponseEntity<?> updateBuilder(Builder builderRef) {
 		Builder builder = builderRepository.findById(builderRef.getId()).orElseThrow(() -> new UserNotFoundException("user Not Found to Update"));
 		if (builder != null) {
 			builder.setName(builderRef.getName());
 			builder.setEmail(builderRef.getEmail());
 			builder.setContactInfo(builderRef.getContactInfo());
 			builder.setCompanyName(builderRef.getCompanyName());
 			
 			
 			builderRepository.save(builder);
 			return new ResponseEntity<>("info Updated", HttpStatus.OK);
 		} else {
 			throw new CustomException("unable to updaate");
 		}
 	}
    
    public UserDetails loadBuilderByname(String name) throws UserNotFoundException {
		Builder builder = builderRepository.findByEmail(name);
		if (builder == null) {
			throw new UserNotFoundException("Builder not found with email: " + name);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(builder.getEmail(), builder.getPassword(), authorities);
	}
    
    public Builder findBuilderProfileByJwt(String jwt) throws UserNotFoundException {
		String email = jwtProviderRef.getEmailFromToken(jwt);
		Builder builder = builderRepository.findByEmail(email);
		if (builder == null) {
			throw new UserNotFoundException("Builder not found with this email: " + email);
		}
		return builder;
}

	public Builder GetByEmail(String mail) {
		Builder builderRef = builderRepository.findByEmail(mail);
		if (builderRef != null) {
			return builderRef;
		} else {

			throw new UserNotFoundException("Builder Not Found");

		}
	}
	
}
