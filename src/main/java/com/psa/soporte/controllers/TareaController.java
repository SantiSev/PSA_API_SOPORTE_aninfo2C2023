package com.psa.soporte.controllers;

import com.psa.soporte.DTO.request.TareaRequest;
import com.psa.soporte.modelos.Tarea;
import com.psa.soporte.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tareas")
public class TareaController {

    private final TareaService tareaService;

    @Autowired
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> getAllTareas() {
        List<Tarea> tareaes = tareaService.getAllTareas();
        return new ResponseEntity<>(tareaes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(@PathVariable Long id) {
        Tarea tarea = tareaService.getTareaById(id);
        return tarea != null ?
                new ResponseEntity<>(tarea, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody TareaRequest tarea) {
        Tarea tareaCreada = tareaService.crearTarea(tarea);
        return new ResponseEntity<>(tareaCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody TareaRequest tareaRequest) {
        Tarea coalboradorActualizada = tareaService.actualizarTarea(id, tareaRequest);
        return coalboradorActualizada != null ?
                new ResponseEntity<>(coalboradorActualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> quitarTarea(@PathVariable Long id) {
        tareaService.quitarTarea(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
