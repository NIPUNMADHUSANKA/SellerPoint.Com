package SellerPoint.Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import SellerPoint.Backend.services.CustomUserDetailsServices;

@Configuration
@EnableWebSecurity
public class security extends WebSecurityConfigurerAdapter{

    /*@Autowired
    private DataSource dataSource;
    */

    @Bean
    public UserDetailsService userDetailsService(){

        return new CustomUserDetailsServices();
        
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
     
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;

    }

    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{


        http.cors().and().csrf().disable().authorizeRequests().antMatchers(
         "/SellerPoint/Register",
         "/SellerPoint/Login",
         "/testing"
         )
		.permitAll()
		.antMatchers("/SellerPoint/Service/**").permitAll()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers("/SellerPoint/Buyer/**").hasAnyAuthority("Buyer");
		http.authorizeRequests().antMatchers("/SellerPoint/Seller/**").hasAnyAuthority("Seller");
		http.authorizeRequests().anyRequest().authenticated();
		
        
    }

    @Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}


}
