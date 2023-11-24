package com.psa.soporte.services;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepo ticketRepo;


    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepo.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepo.findById(id).orElse(null);
    }

    public Ticket crearTicket(TicketRequest ticketRequest) {
        Ticket ticketNuevo = new Ticket();
        ticketNuevo.setNombre(ticketRequest.getNombre());
        return ticketRepo.save(ticketNuevo);
    }

    public Ticket actualizarTicket( Long id, TicketRequest ticketRequest) {
        Ticket ticket = getTicketById(id);
        ticket.setNombre(ticketRequest.getNombre());
        return ticketRepo.save(ticket);
    }

    public void quitarTicket(Long id) {
        ticketRepo.deleteById(id);
    }
}
