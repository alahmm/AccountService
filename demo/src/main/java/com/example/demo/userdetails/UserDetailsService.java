package com.example.demo.userdetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
