package com.example.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.realestate.entity.Feedback;
import com.example.realestate.entity.Property;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProperty(Property property);
    
    @Query("SELECT f FROM Feedback f WHERE f.property.id = :propertyId")
    List<Feedback> findByPropertyId(@Param("propertyId") Long propertyId);
}
