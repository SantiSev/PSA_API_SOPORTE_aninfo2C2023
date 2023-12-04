package com.psa.soporte.controllers;

import com.psa.soporte.DTO.response.ColaboradorResponse;
import com.psa.soporte.services.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "colaboradores")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://psa-front-two.vercel.app/")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    @Autowired
    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping
    public ResponseEntity<List<ColaboradorResponse>> getAllColaboradores() {
        List<ColaboradorResponse> colaboradores = colaboradorService.getAllColaboradors();
        return new ResponseEntity<>(colaboradores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorResponse> getColaboradorById(@PathVariable Long id) {
        ColaboradorResponse colaborador = colaboradorService.getColaboradorResponseById(id);
        return colaborador != null ?
                new ResponseEntity<>(colaborador, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
