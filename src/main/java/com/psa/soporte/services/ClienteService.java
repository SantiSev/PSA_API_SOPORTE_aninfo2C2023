package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.enums.ExceptionMensajes;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.repo.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepo clienteRepo;


    @Autowired
    public ClienteService(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = (List<Cliente>) clienteRepo.findAll();
        if (clientes.isEmpty()){
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return clientes;
    }

    public Cliente getClienteById(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.CLIENTE_NOT_FOUND.getMessage()));
    }

    public List<Ticket> getAllTicketsFromClient(Long id) {
        Cliente cliente = clienteRepo.findById(id).orElse(null);
        assert cliente != null;
        if (cliente.getTickets().isEmpty()){
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return cliente.getTickets();
    }


    public Cliente crearCliente(ClienteRequest clienteRequest) {
        evitarErrorNombre(clienteRequest.getNombre());
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre(clienteRequest.getNombre());
        return clienteRepo.save(clienteNuevo);
    }

    public Cliente actualizarCliente(Long id, ClienteRequest clienteRequest) {
        Cliente cliente = getClienteById(id);
        evitarErrorNombre(clienteRequest.getNombre());
        cliente.setNombre(clienteRequest.getNombre());
        return clienteRepo.save(cliente);
    }

    public void quitarCliente(Long id) {
        clienteRepo.deleteById(id);
    }

    private void evitarErrorNombre(String nombre){
        if(clienteRepo.existsByNombre(nombre)){
            throw new IllegalArgumentException(ExceptionMensajes.CLIENTE_YA_EXISTE.getMessage());
        }
    }
}
