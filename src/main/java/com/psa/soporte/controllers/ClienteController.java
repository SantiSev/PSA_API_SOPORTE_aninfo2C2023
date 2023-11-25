package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return cliente != null ?
                new ResponseEntity<>(cliente, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByClient(@PathVariable Long id) {
        List<Ticket> tickets = clienteService.getAllTicketsFromClient(id);
        return tickets != null ?
                new ResponseEntity<>(tickets, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteRequest cliente) {
        Cliente createdCliente = clienteService.crearCliente(cliente);
        return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest) {
        Cliente clienteActualizada = clienteService.actualizarCliente(id, clienteRequest);
        return clienteActualizada != null ?
                new ResponseEntity<>(clienteActualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarCliente(@PathVariable Long id) {
        clienteService.quitarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
