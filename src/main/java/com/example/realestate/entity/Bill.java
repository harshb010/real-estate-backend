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
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long billId;
	private double amount;
	@Enumerated(EnumType.STRING)
	private BillStatus status;
	private LocalDate billingPeriodStart;
	private LocalDate billingPeriodEnd;
	private LocalDate createdDate;
	@ManyToOne
	@JoinColumn(name = "user_id")
	@Cascade(CascadeType.ALL)
	private User userRef;
	
	
	public enum BillStatus {																																																																																																																																
		PAID, PENDING,UNPAID
	}

	public Bill() {

	}

	public Bill(Long billId, double amount, BillStatus status, LocalDate billingPeriodStart, LocalDate billingPeriodEnd,
			LocalDate createdDate, User userRef) {
		this.billId = billId;
		this.amount = amount;
		this.status = status;
		this.billingPeriodStart = billingPeriodStart;
		this.billingPeriodEnd = billingPeriodEnd;
		this.createdDate = createdDate;
		this.userRef = userRef;																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																							;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	public LocalDate getBillingPeriodStart() {
		return billingPeriodStart;
	}

	public void setBillingPeriodStart(LocalDate billingPeriodStart) {
		this.billingPeriodStart = billingPeriodStart;
	}

	public LocalDate getBillingPeriodEnd() {
		return billingPeriodEnd;
	}

	public void setBillingPeriodEnd(LocalDate billingPeriodEnd) {
		this.billingPeriodEnd = billingPeriodEnd;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public User getUserRef() {
		return userRef;
	}

	public void setUserRef(User userRef) {
		this.userRef = userRef;
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", amount=" + amount + ", status=" + status + ", user=" + userRef + "]";
	}

}
