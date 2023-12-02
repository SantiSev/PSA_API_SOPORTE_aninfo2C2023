package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.ColaboradorRequest;
import com.psa.soporte.modelos.Colaborador;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.services.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "colaboradores")
@CrossOrigin(origins = "http://localhost:3000")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @Autowired
    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping
    public ResponseEntity<List<Colaborador>> getAllColaboradores() {
        List<Colaborador> colaboradores = colaboradorService.getAllColaboradors();
        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> getColaboradorById(@PathVariable Long id) {
        Colaborador colaborador = colaboradorService.getColaboradorById(id);
        return colaborador != null ?
                new ResponseEntity<>(colaborador, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
