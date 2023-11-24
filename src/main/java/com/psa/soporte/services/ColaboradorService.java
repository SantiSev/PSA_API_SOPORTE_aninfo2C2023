package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ColaboradorRequest;
import com.psa.soporte.modelos.Colaborador;
import com.psa.soporte.repo.ColaboradorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    private final ColaboradorRepo colaboradorRepo;


    @Autowired
    public ColaboradorService(ColaboradorRepo colaboradorRepo) {
        this.colaboradorRepo = colaboradorRepo;
    }

    public List<Colaborador> getAllColaboradors() {
        return (List<Colaborador>) colaboradorRepo.findAll();
    }

    public Colaborador getColaboradorById(Long id) {
        return colaboradorRepo.findById(id).orElse(null);
    }

    public Colaborador crearColaborador(ColaboradorRequest colaboradorRequest) {
        Colaborador colaboradorNuevo = new Colaborador();
        colaboradorNuevo.setNombre(colaboradorRequest.getNombre());
        return colaboradorRepo.save(colaboradorNuevo);
    }

    public Colaborador actualizarColaborador( Long id, ColaboradorRequest colaboradorRequest) {
        Colaborador colaborador = getColaboradorById(id);
        colaborador.setNombre(colaboradorRequest.getNombre());
        return colaboradorRepo.save(colaborador);
    }

    public void quitarColaborador(Long id) {
        colaboradorRepo.deleteById(id);
    }
}
