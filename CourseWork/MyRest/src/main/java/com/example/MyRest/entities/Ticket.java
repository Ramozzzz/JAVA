package com.example.MyRest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ticket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {
    @Id
    @Column(nullable = false)
    private Long ticketId;

    @Column(nullable = false)
    private LocalDate contractDate;
    @Column(nullable = false)
    private int sittingPlace;
    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private Route route;

    public Long getTicketId() {
        return ticketId;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public int getSittingPlace() {
        return sittingPlace;
    }

    public int getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public Route getRoute() {
        return route;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public void setSittingPlace(int sittingPlace) {
        this.sittingPlace = sittingPlace;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
