package com.example.realestate.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    private String comments;
    private int rating;
    
    @Temporal(TemporalType.DATE)
    private Date feedbackDate;
    
	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(Long id, User user, Property property, Appointment appointment, String comments, int rating,
			Date feedbackDate) {
		super();
		this.id = id;
		this.user = user;
		this.property = property;
		this.appointment = appointment;
		this.comments = comments;
		this.rating = rating;
		this.feedbackDate = feedbackDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Property getPropertyId() {
		return property;
	}

	public void setPropertyId(Property property) {
		this.property = property;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", user=" + user + ", property=" + property + ", appointment=" + appointment
				+ ", comments=" + comments + ", rating=" + rating + ", feedbackDate=" + feedbackDate + "]";
	}
	
	
	
	
    
    
}
