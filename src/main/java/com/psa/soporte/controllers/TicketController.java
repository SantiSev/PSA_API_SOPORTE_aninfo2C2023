package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> ticketes = ticketService.getAllTickets();
        return new ResponseEntity<>(ticketes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ticket != null ?
                new ResponseEntity<>(ticket, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody TicketRequest ticket) {
        Ticket ticketCreado = ticketService.crearTicket(ticket);
        return new ResponseEntity<>(ticketCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {
        Ticket coalboradorActualizada = ticketService.actualizarTicket(id, ticketRequest);
        return coalboradorActualizada != null ?
                new ResponseEntity<>(coalboradorActualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarTicket(@PathVariable Long id) {
        ticketService.quitarTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
