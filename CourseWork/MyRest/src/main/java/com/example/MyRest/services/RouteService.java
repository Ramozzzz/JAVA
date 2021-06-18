package com.example.MyRest.services;

import com.example.MyRest.entities.Route;
import com.example.MyRest.repositories.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> readAll(){
        return routeRepository.findAll();
    }

    public void create(Route route){
        routeRepository.save(route);
    }

    public Route read(long routeId) {
        return routeRepository.getOne(routeId);
    }

    public boolean update(Route route, long routeId) {
        if (routeRepository.existsById(routeId)) {
            route.setRouteId(routeId);
            routeRepository.save(route);
            return true;
        }

        return false;
    }

    public boolean delete(long routeId) {
        if (routeRepository.existsById(routeId)) {
            routeRepository.deleteById(routeId);
            return true;
        }
        return false;
    }
}
