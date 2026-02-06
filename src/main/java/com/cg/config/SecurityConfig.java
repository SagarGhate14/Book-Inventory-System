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
	            // 1. PUBLIC ACCESS (Everyone can see these)
	            .requestMatchers("/login", "/users/new", "/users/add", "/css/**", "/js/**").permitAll()
	            .requestMatchers("/books/list", "/cart/**").permitAll() // Allow cart and books list
	            
	            // 2. ADMIN ONLY (Restricted paths)
	            .requestMatchers("/users/list").hasRole("ADMIN")
	            .requestMatchers("/books/edit/**", "/books/delete/**").hasRole("ADMIN")
	            
	            // 3. SECURE EVERYTHING ELSE
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .defaultSuccessUrl("/books/list", true) // Redirect to book list after login
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login?logout")
	            .invalidateHttpSession(true) // Clear cart and user data on logout
	            .permitAll()
	        )
	        // 4. CSRF PROTECTION (Crucial for the "Add to Cart" POST form)
	        // Ensure your HTML form includes: <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
	        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); 

	    return http.build();
	}

}
