package com.example.demo.controller;

import com.example.demo.model.Status;
import com.example.demo.model.StatusTask;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.StatusTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trello")
public class StatusTaskController {
    private final StatusTaskRepository statusTaskRepository;

    @Autowired
    public StatusTaskController(StatusTaskRepository statusTaskRepository) {
        this.statusTaskRepository = statusTaskRepository;
    }
    @GetMapping(value = "/statusTask")
    private ResponseEntity<List<StatusTask>> getAll(){
        final List<StatusTask> statusTaskList = statusTaskRepository.findAll();
        return !statusTaskList.isEmpty()
                ? new ResponseEntity<>(statusTaskList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/statusTask/{id}")
    public ResponseEntity<StatusTask> getStatus(@PathVariable Long id) {
        final StatusTask currentStatusTask = statusTaskRepository.findStatusTaskByRowId(id);
        return currentStatusTask != null
                ? new ResponseEntity<>(currentStatusTask, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="statusTask")
    private ResponseEntity<?> create(@RequestBody StatusTask statusTask){
        StatusTask newStatusTask = statusTaskRepository.save(statusTask);
        return new ResponseEntity<>(newStatusTask, HttpStatus.CREATED);
    }
    @PutMapping(value = "statusTask")
    public ResponseEntity<?> put(
            @RequestBody StatusTask statusTask) {
        return new ResponseEntity<>(statusTaskRepository.save(statusTask),HttpStatus.OK);
    }
    @DeleteMapping(value = "statusTask/{id}")
    public ResponseEntity<StatusTask> delete(@PathVariable long id) {
        statusTaskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
