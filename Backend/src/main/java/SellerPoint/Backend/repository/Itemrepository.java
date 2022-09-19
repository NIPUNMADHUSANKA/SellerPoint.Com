package SellerPoint.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SellerPoint.Backend.model.Item;

public interface Itemrepository extends JpaRepository<Item, Long>{ 
    
    

}
