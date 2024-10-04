package com.example.realestate.entity;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="flat")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flatNumber;
    private int bhk;  // e.g., 1BHK, 2BHK
    private double size;  // in square feet
    private double price;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @Cascade(CascadeType.ALL)
    private Property property;

  
	public Flat() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Flat(Long id, String flatNumber, int bhk, double size, double price, boolean isAvailable,
			Property property) {
		super();
		this.id = id;
		this.flatNumber = flatNumber;
		this.bhk = bhk;
		this.size = size;
		this.price = price;
		this.isAvailable = isAvailable;
		this.property = property;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFlatNumber() {
		return flatNumber;
	}


	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}


	public int getBhk() {
		return bhk;
	}


	public void setBhk(int bhk) {
		this.bhk = bhk;
	}


	public double getSize() {
		return size;
	}


	public void setSize(double size) {
		this.size = size;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}


	public Property getProperty() {
		return property;
	}


	public void setProperty(Property property) {
		this.property = property;
	}


	@Override
	public String toString() {
		return "Flat [id=" + id + ", flatNumber=" + flatNumber + ", bhk=" + bhk + ", size=" + size + ", price=" + price
				+ ", isAvailable=" + isAvailable + ", property=" + property + "]";
	}

	
	
   
}
