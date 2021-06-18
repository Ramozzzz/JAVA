package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.Task;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("trello")
public class TaskController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @GetMapping(value = "/task")
    private ResponseEntity<List<Task>> getAll(){
        final List<Task> taskList = taskRepository.findAll();
        return !taskList.isEmpty()
                ? new ResponseEntity<>(taskList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        final Task currentTask = taskRepository.findTaskByRowId(id);
        return currentTask != null
                ? new ResponseEntity<>(currentTask, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value="task")
    private ResponseEntity<?> create(@RequestBody Task task){
        Task newTask = taskRepository.save(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }
    @PutMapping(value = "task")
    public ResponseEntity<?> put(
            @RequestBody Task task) {
        return new ResponseEntity<>(taskRepository.save(task),HttpStatus.OK);
    }
    @DeleteMapping(value = "task/{id}")
    public ResponseEntity<Task> delete(@PathVariable long id) {
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
