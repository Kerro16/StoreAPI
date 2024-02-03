package com.kerro.Store.services;

import com.kerro.Store.exceptions.EmailAlreadyExistsException;
import com.kerro.Store.exceptions.RoleNotFoundException;
import com.kerro.Store.exceptions.UserNotFoundException;
import com.kerro.Store.exceptions.UsernameAlreadyExistsException;
import com.kerro.Store.model.ERole;
import com.kerro.Store.model.Role;
import com.kerro.Store.model.User;
import com.kerro.Store.repository.RoleRepository;
import com.kerro.Store.repository.UserRepository;
import com.kerro.Store.request.UserCreationRequest;
import com.kerro.Store.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    public List<User> getAllUsers(){ return userRepository.findAll(); }

    public void removeUser(Long id){

      Optional<User> userOptional = userRepository.findById(id);

      if (userOptional.isPresent()){
          userRepository.delete(userOptional.get());
      } else {
          throw new UserNotFoundException("User not found with ID: " +id);
      }

    }

    public User addNewUser(UserCreationRequest userCreationRequest) {


        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }

        if (userRepository.existsByEmail(userCreationRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already taken!");
        }

        // Create new user's account
        User user = new User(userCreationRequest.getUsername(),
                userCreationRequest.getEmail(),
                passwordEncoder.encode(userCreationRequest.getPassword()));

        Set<String> strRoles = userCreationRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }

    public void updateUserAdmin(UserUpdateRequest userUpdateRequest) {
        Optional<User> optionalUser = userRepository.findById(userUpdateRequest.getId());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userUpdateRequest.getId() + " not found");
        }

        User existingUser = optionalUser.get();


        if (!userUpdateRequest.getUsername().equals(existingUser.getUsername()) &&
                userRepository.existsByUsername(userUpdateRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username '" + userUpdateRequest.getUsername() + "' already taken!");
        }


        Set<Role> updatedRoles = userUpdateRequest.getRole().stream()
                .map(roleName -> new Role(ERole.valueOf(roleName)))
                .collect(Collectors.toSet());


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
