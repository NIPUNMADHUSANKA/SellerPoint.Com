package sellerpoint.com.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sellerpoint.com.Backend.model.Customer;
import sellerpoint.com.Backend.repository.CustomerRepository;

@RestController
@RequestMapping("/ap1/v1/")
public class CustomerController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCusotmer(){
        

        return customerRepository.findAll();
    }
    
}
