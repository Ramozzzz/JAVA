package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trello")
public class RoleController {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @GetMapping(value = "/role")
    private ResponseEntity<List<Role>> getAll(){
        final List<Role> roleList = roleRepository.findAll();
        return !roleList.isEmpty()
                ? new ResponseEntity<>(roleList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/role/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        final Role currentRole = roleRepository.findRoleByRowId(id);
        return currentRole != null
                ? new ResponseEntity<>(currentRole, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="role")
    private ResponseEntity<?> create(@RequestBody Role role){
        Role newRole = roleRepository.save(role);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }
    @PutMapping(value = "role")
    public ResponseEntity<?> put(
                                  @RequestBody Role role) {
        return new ResponseEntity<>(roleRepository.save(role),HttpStatus.OK);
    }
    @DeleteMapping(value = "role/{id}")
    public ResponseEntity<Role> delete(@PathVariable long id) {
        roleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
