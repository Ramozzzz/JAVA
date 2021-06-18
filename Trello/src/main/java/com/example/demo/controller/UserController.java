package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("trello")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping(value = "/user")
    private ResponseEntity<List<User>> getAll(){
        final List<User> userList = userRepository.findAll();
        return !userList.isEmpty()
                ? new ResponseEntity<>(userList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        final User currentUser = userRepository.findUserByRowId(id);
        return currentUser != null
                ? new ResponseEntity<>(currentUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="user")
    private ResponseEntity<?> create(@RequestBody User user){
       User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PutMapping(value = "user")
    public ResponseEntity<?> put(
            @RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user),HttpStatus.OK);
    }
    @DeleteMapping(value = "user/{id}")
    public ResponseEntity<User> delete(@PathVariable long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
