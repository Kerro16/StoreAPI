package com.kerro.Store.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){ this.userService = userService;}

    @RequestMapping
    public List<User> getAllUsers(){return userService.getAllUsers();}

    @RequestMapping("/add")
    public ResponseEntity<Void> addNewUsers(@Validated @RequestBody List<User> userList){
        userService.addNewUsers(userList);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/remove")
    public ResponseEntity<List<User>> removeUsers(@Validated @RequestBody List<User> userList){
        List<User> removedUJsers = userService.removeUser(userList);
        return new ResponseEntity<>(removedUJsers, HttpStatus.OK);
    }

    @RequestMapping("/update")
    public  ResponseEntity<List<User>> updateUsers(@Validated @RequestBody List<User> userList){
        List<User> updatedUsers = userService.updateUser(userList);
        return  new ResponseEntity<>(updatedUsers, HttpStatus.OK);
    }
}
