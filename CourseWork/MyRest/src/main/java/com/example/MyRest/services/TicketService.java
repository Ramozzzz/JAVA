package com.example.MyRest.services;

import com.example.MyRest.entities.Ticket;
import com.example.MyRest.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> readAll(){
        return ticketRepository.findAll();
    }

    public void create(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public Ticket read(long ticketId) {
        return ticketRepository.getOne(ticketId);
    }

    public boolean update(Ticket ticket, long ticketId) {
        if (ticketRepository.existsById(ticketId)) {
            ticket.setTicketId(ticketId);
            ticketRepository.save(ticket);
            return true;
        }

        return false;
    }

    public boolean delete(long ticketId) {
        if (ticketRepository.existsById(ticketId)) {
            ticketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }
}
