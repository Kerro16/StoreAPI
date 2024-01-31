package com.kerro.Store.controllers;


import com.kerro.Store.services.UserService;
import com.kerro.Store.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @RequestMapping("/getall")
    public List<User> getAllUsers(){return userService.getAllUsers();}

    @RequestMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> removeUsers(@Validated @RequestBody List<User> userList){
        List<User> removedUsers = userService.removeUser(userList);
        return new ResponseEntity<>(removedUsers, HttpStatus.OK);
    }

    }