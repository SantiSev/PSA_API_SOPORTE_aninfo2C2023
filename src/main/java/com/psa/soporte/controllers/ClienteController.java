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
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

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




}
