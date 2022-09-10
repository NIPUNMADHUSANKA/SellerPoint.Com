package SellerPoint.Backend.services;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import SellerPoint.Backend.utils.JwtUtil;

@Component
public class JwtFilterRequest extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsServices customuser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwtToken = authorizationHeader.substring(7);
            email = jwtUtil.extractUsername(jwtToken);
            
        }

        
      

        if (email != null) {
            
            UserDetails currentUserDetails = customuser.loadUserByUsername(email);
            Boolean tokenValidation = jwtUtil.validateToken(jwtToken, currentUserDetails);

            if (tokenValidation) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            			new UsernamePasswordAuthenticationToken(currentUserDetails, null,currentUserDetails.getAuthorities());
            	
            	usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            	
            	SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }

        filterChain.doFilter(request, response);
    }

}
