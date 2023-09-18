package com.example.demo.userdetails;

import com.example.demo.businesslayout.MyUser;
import com.example.demo.persistence.MyUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {
        private final MyUserRepository repository;

    public AppUserDetailsServiceImpl(MyUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = repository
                .findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new AppUserAdapter(user);
    }
}
