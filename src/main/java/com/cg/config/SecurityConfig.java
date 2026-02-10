package com.cg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            // 1. PUBLIC ACCESS (Added /favicon.ico and static resources)
	            .requestMatchers("/login", "/users/new", "/users/add","/users/verify-corp","/users/newAdmin","/users/addAdmin").permitAll()
	            
	            
	            // 2. ADMIN ONLY
	            .requestMatchers("/users/list","users/delete/**").hasRole("ADMIN")
	            .requestMatchers("/books/edit/**", "/books/delete/**").hasRole("ADMIN")
	            .requestMatchers("/authors/edit/**","/authors/delete/**").hasRole("ADMIN")
	            .requestMatchers("/category/edit/**","/category/delete/**").hasRole("ADMIN")
	            .requestMatchers("/publishers/edit/**","/publishers/delete/**").hasRole("ADMIN")
	            .requestMatchers("/inventories/edit/**","/inventories/delete/**").hasRole("ADMIN")
	            
	        
	            
	            // 3. SECURE EVERYTHING ELSE
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login")  
	            .defaultSuccessUrl("/books/list", true) 
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login?logout")
	            .invalidateHttpSession(true) 
	            .permitAll()
	        );
	        
	    return http.build();
	}
}
