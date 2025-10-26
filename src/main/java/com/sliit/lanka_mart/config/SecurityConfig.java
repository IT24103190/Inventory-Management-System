package com.sliit.lanka_mart.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/supplier-auth/**").permitAll()   // Supplier authentication
                        .requestMatchers("/suppliers/**").permitAll()       // Suppliers endpoints
                        .requestMatchers("/low-stock/**").permitAll()      // Low stock endpoints
                        .requestMatchers("/categories/**").permitAll()
                        .requestMatchers("/dashboard/**").permitAll()      // Dashboard endpoints
                        .requestMatchers("/products/**").permitAll()       // Products endpoints
                        .requestMatchers("/purchase-orders/**").permitAll() // Purchase orders
                        .requestMatchers("/simple-purchase-orders/**").permitAll() // Simple purchase orders
                        .requestMatchers("/sales-orders/**").permitAll()   // Sales orders
                        .requestMatchers("/stock-history/**").permitAll()  // Stock history
                        .requestMatchers("/roles/**").permitAll()          // Roles
                        .requestMatchers("/users/**").permitAll()          // Users
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/", "/index.html", "/*.html", "/*.css", "/*.js", "/*.ico").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}