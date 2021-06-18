package com.example.demo.controller;

import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trello")
public class StatusController {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }
    @GetMapping(value = "/status")
    private ResponseEntity<List<Status>> getAll(){
        final List<Status> statusList = statusRepository.findAll();
        return !statusList.isEmpty()
                ? new ResponseEntity<>(statusList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/status/{id}")
    public ResponseEntity<Status> getStatus(@PathVariable Long id) {
        final Status currentStatus = statusRepository.findStatusByRowId(id);
        return currentStatus != null
                ? new ResponseEntity<>(currentStatus, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="status")
    private ResponseEntity<?> create(@RequestBody Status status){
        Status newStatus = statusRepository.save(status);
        return new ResponseEntity<>(newStatus, HttpStatus.CREATED);
    }
    @PutMapping(value = "status")
    public ResponseEntity<?> put(
            @RequestBody Status status) {
        return new ResponseEntity<>(statusRepository.save(status),HttpStatus.OK);
    }
    @DeleteMapping(value = "status/{id}")
    public ResponseEntity<Status> delete(@PathVariable long id) {
        statusRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
