package com.pg.securityauth.controller;

import com.pg.securityauth.entity.Roles;
import com.pg.securityauth.entity.Users;
import com.pg.securityauth.exception.SecurityAuthException;
import com.pg.securityauth.model.AuthRequest;
import com.pg.securityauth.model.AuthResponse;
import com.pg.securityauth.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SecurityAuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/auth/token")
    public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest authRequest){

        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            User user = (User) auth.getPrincipal();
            Users users = new Users(user.getUsername(), user.getPassword(), getUserRoles(user.getAuthorities()));
            String jwtToken = jwtUtils.generateAccessToken(users);
            return new ResponseEntity<>(new AuthResponse(jwtToken), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SecurityAuthException();
        }
    }

    private Set<Roles> getUserRoles(Collection<? extends GrantedAuthority> authorities) {

        Set<Roles> roles = new HashSet<>();

        for(GrantedAuthority auth: authorities) {
            roles.add(new Roles(auth.getAuthority()));
        }

        return roles;

    }
}
