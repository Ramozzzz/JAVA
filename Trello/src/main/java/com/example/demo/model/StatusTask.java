package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="s_status_task")
@Data
@NoArgsConstructor
public class StatusTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rowId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="status_id",nullable = false)
    private Status status;

    @OneToMany(mappedBy = "statusTask")
    private Set<Task> tasks;

}
