package com.psa.soporte.services;

import com.psa.soporte.DTO.request.TareaRequest;
import com.psa.soporte.modelos.Tarea;
import com.psa.soporte.repo.TareaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepo tareaRepo;


    @Autowired
    public TareaService(TareaRepo tareaRepo) {
        this.tareaRepo = tareaRepo;
    }

    public List<Tarea> getAllTareas() {
        return (List<Tarea>) tareaRepo.findAll();
    }

    public Tarea getTareaById(Long id) {
        return tareaRepo.findById(id).orElse(null);
    }

    public Tarea crearTarea(TareaRequest tareaRequest) {
        Tarea tareaNuevo = new Tarea();
        tareaNuevo.setNombre(tareaRequest.getNombre());
        return tareaRepo.save(tareaNuevo);
    }

    public Tarea actualizarTarea( Long id, TareaRequest tareaRequest) {
        Tarea tarea = getTareaById(id);
        tarea.setNombre(tareaRequest.getNombre());
        return tareaRepo.save(tarea);
    }

    public void quitarTarea(Long id) {
        tareaRepo.deleteById(id);
    }
}
