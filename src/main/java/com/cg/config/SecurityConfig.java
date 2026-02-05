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
            // 1. Pages anyone can see (Registration & Login)
            .requestMatchers("/login", "/users/new", "/users/add", "/css/**", "/js/**").permitAll()
            
            // 2. Specific ADMIN-only pages
            .requestMatchers("/users/list").hasRole("ADMIN")
            
            // 3. SECURE EVERYTHING ELSE (This includes /books/list)
            // If a user isn't logged in and hits /books/list, they WILL be redirected to /login
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/inventories/list", true)
            .permitAll()
        )
        .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
        
    return http.build();
	    }

}
