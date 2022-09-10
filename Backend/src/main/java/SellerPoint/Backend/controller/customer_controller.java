package SellerPoint.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import SellerPoint.Backend.model.customer;
import SellerPoint.Backend.repository.customerrepository;
import SellerPoint.Backend.response.LoginResponse;
import SellerPoint.Backend.services.CustomUserDetailsServices;
import SellerPoint.Backend.utils.JwtUtil;


@RestController
public class customer_controller {

    @Autowired
    private customerrepository customerrepository;

    @Autowired
	private AuthenticationManager authenticationManager;
 
    @Autowired
	private CustomUserDetailsServices customUserDetailsServices;

    @Autowired
	private JwtUtil jwtUtil;

    @PostMapping("/SellerPoint/Login")
	private ResponseEntity<?> authenticateClient(@RequestBody customer cus){
		
		String email = cus.getEmail();
		String password = cus.getPassword();
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		}catch(Exception e) {
			
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		UserDetails loadedUser = customUserDetailsServices.loadUserByUsername(email);
		
		String name = loadedUser.getUsername();

        String generatedToken = jwtUtil.generateToken(name);
		
		LoginResponse response = new LoginResponse();
		response.setToken(generatedToken);
				
		return ResponseEntity.ok(response);
		
	}
    

    @PostMapping("/SellerPoint/Register")
    public ResponseEntity<?> processRegistraction(@RequestBody customer cus){

        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String encodepassword = encode.encode(cus.getPassword());
        cus.setPassword(encodepassword);

        
        try{
            customerrepository.save(cus);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        

        
    }


    @GetMapping("/testing")
	private String Testing() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return userName;
        
		
	}

    
}
