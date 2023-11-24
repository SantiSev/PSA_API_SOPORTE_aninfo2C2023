package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.repo.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepo clienteRepo;


    @Autowired
    public ClienteService(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<Cliente> getAllClientes() {
        return (List<Cliente>) clienteRepo.findAll();
    }

    public Cliente getClienteById(Long id) {
        return clienteRepo.findById(id).orElse(null);
    }

    public Cliente crearCliente(ClienteRequest clienteRequest) {
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre(clienteRequest.getNombre());
        return clienteRepo.save(clienteNuevo);
    }

    public Cliente actualizarCliente(Long id, ClienteRequest clienteRequest) {
        Cliente cliente = getClienteById(id);
        cliente.setNombre(clienteRequest.getNombre());
        return clienteRepo.save(cliente);
    }

    public void quitarCliente(Long id) {
        clienteRepo.deleteById(id);
    }
}
