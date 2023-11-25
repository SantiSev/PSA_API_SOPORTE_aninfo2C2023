package com.psa.soporte.services;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.enums.*;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.repo.TicketRepo;
import com.psa.soporte.tools.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepo ticketRepo;


    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = (List<Ticket>) ticketRepo.findAll();
        if (tickets.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return tickets;
    }

    public Ticket getTicketById(Long id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage()));
    }

    public Ticket crearTicket(TicketRequest ticketRequest) {

        Ticket ticketNuevo = new Ticket(ticketRequest);

        return ticketRepo.save(ticketNuevo);
    }

    public Ticket actualizarTicket(Long id, TicketRequest ticketRequest) {
        Ticket ticket = getTicketById(id);
        Optional.ofNullable(ticketRequest.getNombre()).ifPresent(ticket::setNombre);
        Optional.ofNullable(ticketRequest.getDescripcion()).ifPresent(ticket::setDescripcion);

        if (!ticketRequest.getPrioridad().isBlank()) {
            Validacion.validarEnum(ticketRequest.getPrioridad(), Prioridad.class);
            ticket.setPrioridad(Prioridad.valueOf(ticketRequest.getPrioridad().toUpperCase()));
        }

        if (!ticketRequest.getSeveridad().isBlank()) {
            Validacion.validarEnum(ticketRequest.getSeveridad(), Severidad.class);
            ticket.setSeveridad(Severidad.valueOf(ticketRequest.getSeveridad().toUpperCase()));
        }

        if (!ticketRequest.getCategoria().isBlank()) {
            Validacion.validarEnum(ticketRequest.getCategoria(), Categoria.class);
            ticket.setCategoria(Categoria.valueOf(ticketRequest.getCategoria().toUpperCase()));

        }

        if (!ticketRequest.getEstado().isBlank()) {
            Validacion.validarEnum(ticketRequest.getEstado(), Estado.class);
            ticket.setEstado(Estado.valueOf(ticketRequest.getEstado().toUpperCase()));
        }

        return ticketRepo.save(ticket);
    }


    public void quitarTicket(Long id) {
        ticketRepo.deleteById(id);
    }
}
