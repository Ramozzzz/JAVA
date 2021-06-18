package com.example.MyRest.repositories;

import com.example.MyRest.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {}
