package com.example.demo.repository;

import com.example.demo.model.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTaskRepository extends JpaRepository<StatusTask, Long> {
    StatusTask findStatusTaskByRowId(long id);
}
