package com.example.MyRest.controllers;

import com.example.MyRest.entities.Train;
import com.example.MyRest.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainController {

    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping(value = "/train")
    public ResponseEntity<?> create(@RequestBody Train train) {
        trainService.create(train);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/train")
    public ResponseEntity<List<Train>> read() {
        final List<Train> trains = trainService.readAll();

        return trains != null &&  !trains.isEmpty()
                ? new ResponseEntity<>(trains, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/train/{trainId}")
    public ResponseEntity<Train> read(@PathVariable(name = "trainId") long trainId) {
        final Train train = trainService.read(trainId);

        return train != null
                ? new ResponseEntity<>(train, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/train/{trainId}")
    public ResponseEntity<?> update(@PathVariable(name = "trainId") long trainId, @RequestBody Train train) {
        final boolean updated = trainService.update(train, trainId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/train/{trainId}")
    public ResponseEntity<?> delete(@PathVariable(name = "trainId") long trainId) {
        final boolean deleted = trainService.delete(trainId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
