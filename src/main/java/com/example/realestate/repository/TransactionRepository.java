package com.example.realestate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.realestate.entity.Bill;
import com.example.realestate.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBillRef(Bill billRef);
    
    @Query("SELECT t FROM Transaction t WHERE t.billRef.id = :billId")
    List<Transaction> findByBillId(@Param("billId") Long billId);

}