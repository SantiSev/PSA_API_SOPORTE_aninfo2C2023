package com.psa.soporte.services;

import com.psa.soporte.DTO.request.ClienteRequest;
import com.psa.soporte.DTO.response.ClienteResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.ExceptionMensajes;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.repo.ClienteRepo;
import com.psa.soporte.tools.Converter;
import com.psa.soporte.tools.FetchResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepo clienteRepo;


    @Autowired
    public ClienteService(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<ClienteResponse> getAllClientes() {
        List<Cliente> clientes = (List<Cliente>) clienteRepo.findAll();
        if (clientes.isEmpty()){
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToClienteResponseList(clientes);
    }

    public ClienteResponse getClienteResponseById(Long id) {
        return Converter.convertToClienteResponse(clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.CLIENTE_NOT_FOUND.getMessage())));
    }

    public Cliente getClienteById(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.CLIENTE_NOT_FOUND.getMessage()));
    }

    public List<TicketResponse> getAllTicketsFromClient(Long id) {
        Cliente cliente = clienteRepo.findById(id).orElse(null);
        assert cliente != null;
        if (cliente.getTickets().isEmpty()){
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(cliente.getTickets());
    }


    public ClienteResponse crearCliente(ClienteRequest clienteRequest) {
        evitarErrorNombre(clienteRequest);
        Cliente clienteNuevo = new Cliente(clienteRequest);
        return Converter.convertToClienteResponse(clienteRepo.save(clienteNuevo));
    }

    public ClienteResponse actualizarCliente(Long id, ClienteRequest clienteRequest) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.CLIENTE_NOT_FOUND.getMessage()));
        evitarErrorNombre(clienteRequest);
        cliente.setRazonSocial(clienteRequest.getRazonSocial());
        cliente.setCUIT(clienteRequest.getCUIT());
        return Converter.convertToClienteResponse(clienteRepo.save(cliente));
    }

    public void quitarCliente(Long id) {
        clienteRepo.deleteById(id);
    }

    public List<ClienteResponse> procesarClientes() {
        List<ClienteRequest> requests = FetchResources.processClients();
        List<ClienteResponse> clientes = new ArrayList<>();

        for (ClienteRequest request: requests) {

            if (!clienteRepo.existsByCUIT(request.getCUIT()) || !clienteRepo.existsByRazonSocial(request.getRazonSocial())){
                clientes.add(crearCliente(request));
            }
        }
        return clientes;
    }

    private void evitarErrorNombre(ClienteRequest clienteRequest){
        if(clienteRepo.existsByRazonSocial(clienteRequest.getRazonSocial()) || clienteRepo.existsByCUIT(clienteRequest.getCUIT())){
            throw new IllegalArgumentException(ExceptionMensajes.CLIENTE_YA_EXISTE.getMessage());
        }
    }
}
