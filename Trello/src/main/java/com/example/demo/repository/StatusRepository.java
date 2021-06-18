package com.example.demo.repository;

import com.example.demo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findStatusByRowId(long id);
}
