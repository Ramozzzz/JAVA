package com.example.MyRest.controllers;

import com.example.MyRest.entities.Ticket;
import com.example.MyRest.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/ticket")
    public ResponseEntity<?> create(@RequestBody Ticket ticket) {
        ticketService.create(ticket);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/ticket")
    public ResponseEntity<List<Ticket>> read() {
        final List<Ticket> tickets = ticketService.readAll();

        return tickets != null &&  !tickets.isEmpty()
                ? new ResponseEntity<>(tickets, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/ticket/{ticketId}")
    public ResponseEntity<Ticket> read(@PathVariable(name = "ticketId") long ticketId) {
        final Ticket ticket = ticketService.read(ticketId);

        return ticket != null
                ? new ResponseEntity<>(ticket, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/ticket/{ticketId}")
    public ResponseEntity<?> update(@PathVariable(name = "ticketId") long ticketId, @RequestBody Ticket ticket) {
        final boolean updated = ticketService.update(ticket, ticketId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/ticket/{ticketId}")
    public ResponseEntity<?> delete(@PathVariable(name = "ticketId") long ticketId) {
        final boolean deleted = ticketService.delete(ticketId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
