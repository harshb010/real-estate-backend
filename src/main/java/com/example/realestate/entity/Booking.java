package com.example.realestate.entity;

import java.time.LocalDate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate bookingDate;
	
	public enum BookingStatus {
		CONFIRMED , PENDING, CANCELLED,CHECKEDOUT
	}

	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@Cascade(CascadeType.ALL)
	private User user;

	@ManyToOne
	@JoinColumn(name = "flat_id")
	@Cascade(CascadeType.ALL)
	private Flat flatRef;

	public Booking() {

	}

	public Booking(Long id, LocalDate bookingDate, User user) {
		this.id = id;
		this.bookingDate = bookingDate;
		this.user = user;
	}

	

	public Booking(Long id, LocalDate bookingDate, BookingStatus bookingStatus,User user, Flat flatRef) {
		this.id = id;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.user = user;
		this.flatRef = flatRef;
	}

	public Flat getFlatRef() {
		return flatRef;
	}

	public void setRoomRef(Flat flatRef) {
		this.flatRef = flatRef;
	}

	public Long getBookingId() {
		return id;
	}

	public void setBookingId(Long id) {
		this.id = id;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public User getUserRef() {
		return user;
	}

	public void setUserRef(User user) {
		this.user = user;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}


	@Override
	public String toString() {
		return "Booking [bookingId=" + id + ", bookingDate=" + bookingDate + 
				  ", bookingStatus=" + bookingStatus + ", userRef=" + user + ", flatRef=" + flatRef + "]";
	}

	

}
