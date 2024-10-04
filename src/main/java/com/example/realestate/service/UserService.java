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
import com.example.realestate.entity.User;
import com.example.realestate.exception.CustomException;
import com.example.realestate.exception.EmailAlreadyExistException;
import com.example.realestate.exception.UserNotFoundException;
import com.example.realestate.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtProvider jwtProviderRef;
    
    @Autowired
    private PasswordEncoder encoder;

    public User registerUser(User newUser) {
    	User user = userRepository.findByEmail(newUser.getEmail());
    	if(user == null)
    	{
    		return userRepository.save(newUser);
    	}
    	else {
    		throw new EmailAlreadyExistException("An account with this Email already exists");
    	}
        
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public List<User> GetAllUsersByRole(String role) {
		List<User> userList = userRepository.findByRole(role);
		if (userList.size() != 0)
			return userList;
		else {
			throw new UserNotFoundException("User Not Found");
		}
	}
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        if(userList.size()!=0)
        	return userList;
        else {
        	throw new UsernameNotFoundException("User Not Found");
        }
    }
    
    public ResponseEntity<?> deleteUser(Long id) {
       User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
       if(user != null)
       {
    	   userRepository.delete(user);
    	   return new ResponseEntity<>("User Deleted",HttpStatus.OK);   
       }else {
    	   throw new CustomException("Unable to Delete");
       }
    }

    public ResponseEntity<?> updateUser(User userRef) {
		User user = userRepository.findById(userRef.getId()).orElseThrow(() -> new UserNotFoundException("user Not Found to Update"));
		if (user != null) {
			user.setAddress(userRef.getAddress());
			user.setEmail(userRef.getEmail());
			user.setPhoneNumber(userRef.getPhoneNumber());
			user.setFirstName(userRef.getFirstName());
			user.setLastName(userRef.getLastName());
			
			userRepository.save(user);
			return new ResponseEntity<>("info Updated", HttpStatus.OK);
		} else {
			throw new CustomException("unable to updaate");
		}
	}

	public ResponseEntity<?> upadatePassword(String email, String oldPassword,String newPassword) {
		User user = userRepository.findByEmail(email);
		System.out.println(email+","+oldPassword+","+newPassword);
		if (user != null) {
			String	oldPass=user.getPassword();
			
			if (!encoder.matches(oldPass, user.getPassword())) {
				user.setPassword(encoder.encode(newPassword));
			userRepository.save(user);
			return new ResponseEntity<>("Password change successuful", HttpStatus.OK);
			}else {
				throw new CustomException("unable to update password");
			}
		} else {
			throw new CustomException("unable to change password");
		}
	}
	
		public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
			User user = userRepository.findByEmail(username);
			if (user == null) {
				throw new UserNotFoundException("User not found with email: " + username);
			}
			List<GrantedAuthority> authorities = new ArrayList<>();
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		}

		
		public User findUserProfileByJwt(String jwt) throws UserNotFoundException {
			String email = jwtProviderRef.getEmailFromToken(jwt);
			User user = userRepository.findByEmail(email);
			if (user == null) {
				throw new UserNotFoundException("User not found with this email: " + email);
			}
			return user;
	}

		public User GetByEmail(String mail) {
			User userRef = userRepository.findByEmail(mail);
			if (userRef != null) {
				return userRef;
			} else {

				throw new UserNotFoundException("User Not Found");

			}
		}
		
		
}


    

   

