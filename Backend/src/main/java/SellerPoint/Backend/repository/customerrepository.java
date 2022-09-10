package SellerPoint.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SellerPoint.Backend.model.customer;

public interface customerrepository extends JpaRepository<customer, Long>{

     @Query("SELECT c FROM customer c WHERE c.email = ?1")
    customer findByEmail(String email);

    
    
}
