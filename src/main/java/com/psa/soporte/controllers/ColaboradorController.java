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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "colaboradores")
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

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByColaborador(@PathVariable Long id) {
        List<Ticket> tickets = colaboradorService.getAllTicketsByColaborador(id);
        return tickets != null ?
                new ResponseEntity<>(tickets, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Colaborador> crearColaborador(@RequestBody ColaboradorRequest colaborador) {
        Colaborador colaboradorCreado = colaboradorService.crearColaborador(colaborador);
        return new ResponseEntity<>(colaboradorCreado, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> actualizarColaborador(@PathVariable Long id, @RequestBody ColaboradorRequest colaboradorRequest) {
        Colaborador coalboradorActualizada = colaboradorService.actualizarColaborador(id, colaboradorRequest);
        return coalboradorActualizada != null ?
                new ResponseEntity<>(coalboradorActualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarColaborador(@PathVariable Long id) {
        colaboradorService.quitarColaborador(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/procesar_colaboradores")
    public ResponseEntity<List<Colaborador>> procesarColaboradores() {
        List<Colaborador> colaboradors = colaboradorService.procesarColaboradores();
        return new ResponseEntity<>(colaboradors, HttpStatus.CREATED);
    }


}
