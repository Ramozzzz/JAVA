package com.example.MyRest.controllers;

import com.example.MyRest.entities.Route;
import com.example.MyRest.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping(value = "/route")
    public ResponseEntity<?> create(@RequestBody Route route) {
        routeService.create(route);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/route")
    public ResponseEntity<List<Route>> read() {
        final List<Route> routes = routeService.readAll();

        return routes != null &&  !routes.isEmpty()
                ? new ResponseEntity<>(routes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/route/{routeId}")
    public ResponseEntity<Route> read(@PathVariable(name = "routeId") long routeId) {
        final Route route = routeService.read(routeId);

        return route != null
                ? new ResponseEntity<>(route, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/route/{routeId}")
    public ResponseEntity<?> update(@PathVariable(name = "routeId") long routeId, @RequestBody Route route) {
        final boolean updated = routeService.update(route, routeId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/route/{routeId}")
    public ResponseEntity<?> delete(@PathVariable(name = "routeId") long routeId) {
        final boolean deleted = routeService.delete(routeId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
