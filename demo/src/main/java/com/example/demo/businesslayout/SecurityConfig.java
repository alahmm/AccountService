package com.example.demo.businesslayout;

import com.example.demo.exceptions.CustomAccessDeniedHandler;
import com.example.demo.userdetails.AppUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AppUserDetailsServiceImpl appUserDetailsService;
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/signup")).permitAll() // Allow all users for this endpoint
                        .requestMatchers(new AntPathRequestMatcher("/api/delete")).permitAll() // Allow all users for this endpoint
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/changepass")).hasAnyRole("USER", "ACCOUNTANT", "ADMINISTRATOR")
                        .requestMatchers(new AntPathRequestMatcher("/api/empl/payment")).hasAnyRole("ACCOUNTANT", "USER")
                        .requestMatchers(new AntPathRequestMatcher("/api/acct/payments")).hasRole("ACCOUNTANT")
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/user/**")).hasRole("ADMINISTRATOR")
                        // .requestMatchers(new AntPathRequestMatcher("/api/admin/user/role")).hasAnyRole("ADMINISTRATOR")

                        .anyRequest().permitAll() // Require authentication for this endpoint
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                /*                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // Set the custom access denied handler
                .and()
                 */
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
