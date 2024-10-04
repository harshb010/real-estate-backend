package com.example.realestate.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String description;
    private double price;
    private String url;

    @ManyToOne
    @JoinColumn(name = "builder_id")
    private Builder builder;

    @OneToMany(mappedBy = "property")
    private List<Flat> flats;

    @ManyToOne
    @JoinColumn(name = "propertyType_id")
    private PropertyType propertyType; // e.g., Apartment, Villa

    @ManyToMany
    @JoinTable(
        name = "property_amenity",
        joinColumns = @JoinColumn(name = "builder_id"),
        inverseJoinColumns = @JoinColumn(name = "propertyType_id"))
    private List<Amenity> amenities;

	public Property() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Property(Long id, String name, String address, String city, String state, String zipcode, String description,
			double price, Builder builder, List<Flat> flats, PropertyType propertyType, List<Amenity> amenities, String url) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.description = description;
		this.price = price;
		this.builder = builder;
		this.flats = flats;
		this.propertyType = propertyType;
		this.amenities = amenities;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Builder getBuilder() {
		return builder;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	public List<Flat> getFlats() {
		return flats;
	}

	public void setFlats(List<Flat> flats) {
		this.flats = flats;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	@Override
	public String toString() {
		return "Property [id=" + id + ", name=" + name + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", zipcode=" + zipcode + ", description=" + description + ", price=" + price + ", builder=" + builder
				+ ", flats=" + flats + ", propertyType=" + propertyType + ", amenities=" + amenities + "]";
	}




    
}
