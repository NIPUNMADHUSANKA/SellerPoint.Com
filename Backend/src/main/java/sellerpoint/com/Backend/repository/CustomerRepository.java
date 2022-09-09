package sellerpoint.com.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sellerpoint.com.Backend.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
