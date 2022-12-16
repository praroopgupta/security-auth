package com.pg.securityauth.service;

import com.pg.securityauth.entity.Roles;
import com.pg.securityauth.entity.Users;
import com.pg.securityauth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = repo.findByUsername(username);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return new User(user.get().getUsername(), user.get().getPassword(), getAuthorities(user.get().getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Set<Roles> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}
