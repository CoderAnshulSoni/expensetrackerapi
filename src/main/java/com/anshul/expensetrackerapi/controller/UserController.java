package com.anshul.expensetrackerapi.controller;

import com.anshul.expensetrackerapi.Entity.User;
import com.anshul.expensetrackerapi.Entity.UserModel;
import com.anshul.expensetrackerapi.exception.ResourceNotFoundException;
import com.anshul.expensetrackerapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser() {
        return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel model) {
        return new ResponseEntity<User>(userService.updateUser(model), HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser() throws ResourceNotFoundException {
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
