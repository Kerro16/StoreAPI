package com.kerro.store.controllers;


import com.kerro.store.services.UserService;
import com.kerro.store.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @RequestMapping("/getall")
    @PreAuthorize("hasRole('USER')")
    public List<User> getAllUsers(){return userService.getAllUsers();}


}