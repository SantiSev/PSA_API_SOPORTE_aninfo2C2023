package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ColaboradorRequest;
import com.psa.soporte.enums.ExceptionMensajes;
import com.psa.soporte.modelos.Colaborador;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.repo.ColaboradorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ColaboradorService {

    private final ColaboradorRepo colaboradorRepo;


    @Autowired
    public ColaboradorService(ColaboradorRepo colaboradorRepo) {
        this.colaboradorRepo = colaboradorRepo;
    }

    public List<Colaborador> getAllColaboradors() {
        List<Colaborador> colaboradores = (List<Colaborador>) colaboradorRepo.findAll();
        if (colaboradores.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return colaboradores;
    }

    public Colaborador getColaboradorById(Long id) {
        return colaboradorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.COLABORADOR_NOT_FOUND.getMessage()));
    }

    public Colaborador getColaboradorByLegajo(Long legajo) {
        return colaboradorRepo.findByLegajo(legajo)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.COLABORADOR_NOT_FOUND.getMessage()));
    }

    public List<Ticket> getAllTicketsByColaborador(Long id) {
        Colaborador colaborador = colaboradorRepo.findById(id).orElse(null);
        assert colaborador != null;
        if (colaborador.getTickets().isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return colaborador.getTickets();
    }

    public Colaborador crearColaborador(ColaboradorRequest colaboradorRequest) {
        evitarErrorLegajo(colaboradorRequest.getLegajo());
        Colaborador colaboradorNuevo = new Colaborador();
        colaboradorNuevo.setNombre(colaboradorRequest.getNombre());
        return colaboradorRepo.save(colaboradorNuevo);
    }

    public Colaborador actualizarColaborador(Long id, ColaboradorRequest colaboradorRequest) {
        Colaborador colaborador = getColaboradorById(id);
        evitarErrorLegajo(colaboradorRequest.getLegajo());
        if (colaboradorRequest.getLegajo() != null) {
            evitarErrorLegajo(colaboradorRequest.getLegajo());
            colaborador.setLegajo(colaboradorRequest.getLegajo());
        }
        if (colaboradorRequest.getNombre() != null) {
            colaborador.setNombre(colaboradorRequest.getNombre());
        }
        return colaboradorRepo.save(colaborador);
    }

    public void quitarColaborador(Long id) {
        colaboradorRepo.deleteById(id);
    }


    private void evitarErrorLegajo(Long legajo) {
        if (colaboradorRepo.existsByLegajo(legajo)) {
            throw new IllegalArgumentException(ExceptionMensajes.COLABORADOR_YA_EXISTE.getMessage());
        }
    }
}
