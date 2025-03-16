package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.AuthModel;
import com.anshul.expensetrackerapi.Entity.JwtResponse;
import com.anshul.expensetrackerapi.Entity.User;
import com.anshul.expensetrackerapi.Entity.UserModel;
import com.anshul.expensetrackerapi.security.CustomUserDetailsService;
import com.anshul.expensetrackerapi.service.UserService;
import com.anshul.expensetrackerapi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {

        authenticate(authModel.getEmail(), authModel.getPassword());

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authModel.getEmail());

        final String jwtToken = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JwtResponse>(new JwtResponse(jwtToken), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e){
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e){
            throw new Exception("Bad credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel model) {
        return new ResponseEntity<User>(userService.createUser(model), HttpStatus.CREATED);
    }
}
