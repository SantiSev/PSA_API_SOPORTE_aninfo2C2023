package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tickets")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://psa-front-nine.vercel.app/")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        TicketResponse ticket = ticketService.getTicketById(id);
        return ticket != null ?
                new ResponseEntity<>(ticket, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("producto/{producto_id}")
    public ResponseEntity<List<TicketResponse>> getAllTicketsByVersion(@PathVariable Long producto_id) {
        return new ResponseEntity<>(ticketService.getAllTicketsByProducto(producto_id), HttpStatus.OK);
    }

    @PostMapping("{version_id}")
    public ResponseEntity<TicketResponse> crearTicket(@PathVariable Long version_id, @RequestBody TicketRequest ticket) {
        TicketResponse ticketCreado = ticketService.crearTicket(ticket, version_id);
        return new ResponseEntity<>(ticketCreado, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TicketResponse> actualizarTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {
        TicketResponse actualizarTicket = ticketService.actualizarTicket(id, ticketRequest);
        return actualizarTicket != null ?
                new ResponseEntity<>(actualizarTicket, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> quitarTicket(@PathVariable Long id) {
        ticketService.quitarTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
