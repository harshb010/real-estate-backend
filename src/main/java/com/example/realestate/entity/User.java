package com.example.realestate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;		
	    private String password;
	    private String email;
	    private String firstName;
	    private String lastName;
	    private String phoneNumber;
	    private String address;

	    @Enumerated(EnumType.STRING)
	    private Role role; 

		public User() {
			super();
			// TODO Auto-generated constructor stub
		}

		public User(Long id, String password, String email, String firstName, String lastName,
				String phoneNumber, String address, Role role) {
			super();
			this.id = id;
			
			this.password = password;
			this.email = email;
			this.firstName = firstName;
			this.lastName = lastName;
			this.phoneNumber = phoneNumber;
			this.address = address;
			this.role = role;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", password=" + password + ", email=" + email
					+ ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
					+ ", address=" + address + ", role=" + role + "]";
		}
	    
	    
}
