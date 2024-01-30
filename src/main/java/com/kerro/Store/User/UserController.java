package com.kerro.Store.User;


import com.kerro.Store.Security.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.security.Key;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){ this.userService = userService;}

    @Autowired
    private PrivateKey privateKey;

    @RequestMapping
    public List<User> getAllUsers(){return userService.getAllUsers();}

    @RequestMapping("/add")
    public ResponseEntity<Map<String, Object>> addNewUsers(@Validated @RequestBody List<User> userList){
        userService.addNewUsers(userList);
        Map<String, Object> response = new HashMap<>();
        response.put("ok", "true");
        response.put("message", "User added successfully");
        return ResponseEntity.ok(response);
    }

    @RequestMapping("/remove")
    public ResponseEntity<List<User>> removeUsers(@Validated @RequestBody List<User> userList){
        List<User> removedUsers = userService.removeUser(userList);
        return new ResponseEntity<>(removedUsers, HttpStatus.OK);
    }

    @RequestMapping("/update")
    public  ResponseEntity<List<User>> updateUsers(@Validated @RequestBody List<User> userList){
        List<User> updatedUsers = userService.updateUser(userList);
        return  new ResponseEntity<>(updatedUsers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public  ResponseEntity<?> login(@Validated @RequestBody User user) {

        if(userService.usersLogin(user)){

        String token = generateToken(user.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }
        return ResponseEntity.status(401).build(); //Unauthorized
  }

  private String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(privateKey, SignatureAlgorithm.ES256)
                .compact();
  }
}