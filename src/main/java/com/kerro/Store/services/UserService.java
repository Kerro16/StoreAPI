package com.kerro.Store.services;

import com.kerro.Store.model.User;
import com.kerro.Store.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public List<User> getAllUsers(){ return userRepository.findAll(); }


    public List<User> removeUser(List<User> userList){
        List<User> removedUsers = new ArrayList<>();

        for (User user : userList){
            Optional<User> userOptional = userRepository.findById(user.getId());

            if(userOptional.isPresent()){
                userRepository.deleteById(user.getId());
                removedUsers.add(user);
            }
        }
        if(removedUsers.isEmpty()){
            throw new IllegalStateException("No user were found for removal");
        }
        return userList;
    }



}
