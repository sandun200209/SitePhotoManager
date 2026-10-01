package com.sitephotomanager.config;

import com.sitephotomanager.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                // Static resources & PWA assets
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/icons/**",
                    "/images/**",
                    "/uploads/**",
                    "/manifest.json",
                    "/service-worker.js",
                    "/favicon.ico"
                ).permitAll()

                // Public auth endpoints
                .requestMatchers("/login", "/error").permitAll()

                // Admin-only management endpoints
                .requestMatchers("/users/**", "/settings/**").hasRole("ADMIN")

                // Project & Plan modifications: ADMIN and ENGINEER
                .requestMatchers("/projects/new", "/projects/create", "/projects/*/edit", "/projects/*/delete").hasAnyRole("ADMIN", "ENGINEER")
                .requestMatchers("/floor-plans/upload", "/floor-plans/*/delete").hasAnyRole("ADMIN", "ENGINEER")
                .requestMatchers("/rooms/new", "/rooms/create", "/rooms/*/edit", "/rooms/*/delete").hasAnyRole("ADMIN", "ENGINEER")

                // Photo upload/delete: ADMIN, ENGINEER, SUPERVISOR
                .requestMatchers("/photos/upload", "/photos/*/delete").hasAnyRole("ADMIN", "ENGINEER", "SUPERVISOR")

                // Read access for projects, plans, rooms, photos, dashboard: all authenticated roles (including VIEWER)
                .requestMatchers("/dashboard", "/projects/**", "/floor-plans/**", "/rooms/**", "/photos/**").hasAnyRole("ADMIN", "ENGINEER", "SUPERVISOR", "VIEWER")

                // Any other request must be authenticated
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}
