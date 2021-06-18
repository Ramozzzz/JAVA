package com.example.MyRest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "train")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Train {
    @Id
    @Column(nullable = false)
    private Long trainId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private int numberOfPlaces;

    public Long getTrainId() {
        return trainId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }
}
