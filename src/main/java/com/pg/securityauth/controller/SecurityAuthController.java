package com.pg.securityauth.controller;

import com.pg.securityauth.model.AuthRequest;
import com.pg.securityauth.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityAuthController {
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(new AuthResponse(""), HttpStatus.OK);
    }
}
