package SellerPoint.Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SellerPoint.Backend.model.customer;
import SellerPoint.Backend.repository.customerrepository;
import SellerPoint.Backend.security.CustomUserDetails;

@Service
public class CustomUserDetailsServices implements UserDetailsService{

    @Autowired
    private customerrepository cusrepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        customer cus = cusrepo.findByEmail(email);

        if(cus == null){
            throw new UsernameNotFoundException("User not Found");
        }

        return new CustomUserDetails(cus);

    }
    
}
