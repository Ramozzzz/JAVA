package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name= "s_task")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rowId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    
    @ManyToOne
    @JoinColumn(name="developer_id")
    private User developer;
    @ManyToOne
    @JoinColumn(name="analyst_id")
    private User analyst;

    @ManyToOne
    @JoinColumn(name="tester_id")
    private User tester;

    @ManyToOne
    @JoinColumn(name="status_id")
    private StatusTask statusTask;


}
