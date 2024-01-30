package com.kerro.Store.User;

import com.kerro.Store.Product.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){this.userRepository = userRepository;}

    public List<User> getAllUsers(){ return userRepository.findAll(); }

    public List<User> addNewUsers(List<User> userList) {
        List<User> addedUsers = new ArrayList<>();

        for (User user : userList) {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new DuplicateKeyException("Product already exists: " + user.getUsername());
            }
            addedUsers.add(userRepository.save(user));
        }

        return addedUsers;
    }

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

    public List<User> updateUser(List<User> userList){
        List<User> updatedUsers = new ArrayList<>();

        for (User user : userList){
            Optional<User> userOptional = userRepository.findById(user.getId());

            if(userOptional.isPresent()){
                userRepository.save(user);
                updatedUsers.add(user);
            }
        }
        if(updatedUsers.isEmpty()){
            throw new IllegalStateException("No users were found for update");
        }
        return userList;
    }

    public boolean usersLogin(User user){

        Optional<User> userOptional = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(userOptional.isPresent()){
            return true;
        }
        return  false;
    }

}
