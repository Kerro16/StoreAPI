package com.kerro.Store.controllers;


import com.kerro.Store.exceptions.UserNotFoundException;
import com.kerro.Store.exceptions.UsernameAlreadyExistsException;
import com.kerro.Store.model.ERole;
import com.kerro.Store.model.Role;
import com.kerro.Store.repository.UserRepository;
import com.kerro.Store.request.UserUpdateRequest;
import com.kerro.Store.services.UserService;
import com.kerro.Store.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final  UserService userService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/getall")
    @PreAuthorize("hasRole('USER')")
    public List<User> getAllUsers(){return userService.getAllUsers();}

    public void updateUserAdmin(UserUpdateRequest userUpdateRequest) {
        Optional<User> optionalUser = userRepository.findById(userUpdateRequest.getId());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userUpdateRequest.getId() + " not found");
        }

        User existingUser = optionalUser.get();

        // Validar otros campos según sea necesario

        if (!userUpdateRequest.getUsername().equals(existingUser.getUsername()) &&
                userRepository.existsByUsername(userUpdateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username '" + userUpdateRequest.getUsername() + "' already taken!");
        }

        // Convertir los nombres de roles a objetos Role
        Set<Role> updatedRoles = userUpdateRequest.getRole().stream()
                .map(roleName -> new Role(ERole.valueOf(roleName)))
                .collect(Collectors.toSet());

        // Actualizar el nombre de usuario y el rol
        existingUser.setUsername(userUpdateRequest.getUsername());
        existingUser.setRoles(updatedRoles);

        userRepository.save(existingUser);
    }

    public void updateUserPasswordAndEmail(UserUpdateRequest userUpdateRequest) {
        Optional<User> optionalUser = userRepository.findById(userUpdateRequest.getId());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userUpdateRequest.getId() + " not found");
        }

        User existingUser = optionalUser.get();


        existingUser.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        existingUser.setEmail(userUpdateRequest.getEmail());

        userRepository.save(existingUser);
    }

}