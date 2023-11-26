package com.psa.soporte.services;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.*;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Colaborador;
import com.psa.soporte.modelos.Producto;
import com.psa.soporte.modelos.Ticket;
import com.psa.soporte.repo.ProductoRepo;
import com.psa.soporte.repo.TicketRepo;
import com.psa.soporte.tools.Converter;
import com.psa.soporte.tools.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepo ticketRepo;
    private final ProductoRepo productoRepo;
    private final ClienteService clienteService;
    private final ColaboradorService colaboradorService;

    @Autowired
    public TicketService(TicketRepo ticketRepo, ClienteService clienteService, ColaboradorService colaboradorService, ProductoRepo productoRepo) {
        this.ticketRepo = ticketRepo;
        this.clienteService = clienteService;
        this.colaboradorService = colaboradorService;
        this.productoRepo = productoRepo;
    }


    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = (List<Ticket>) ticketRepo.findAll();
        if (tickets.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(tickets);
    }

    public List<TicketResponse> getAllTickets(Long producto_id) {
        List<Ticket> tickets = productoRepo.findById(producto_id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage())).getTickets();
        if (tickets.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(tickets);
    }

    public TicketResponse getTicketById(Long id) {
        return Converter.convertToTicketResponse(ticketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage())));
    }

    public TicketResponse crearTicket(TicketRequest ticketRequest, Long producto_id) {

        Producto producto = productoRepo.findById(producto_id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        Ticket ticketNuevo = new Ticket(ticketRequest);


        if (!ticketRequest.getCliente_id().equals(0L)){
            Cliente cliente = clienteService.getClienteById(ticketRequest.getCliente_id());
            ticketNuevo.setCliente(cliente);
        }
        if (!ticketRequest.getColaborador_id().equals(0L)){
            Colaborador colaborador = colaboradorService.getColaboradorById(ticketRequest.getColaborador_id());
            ticketNuevo.setColaborador(colaborador);
        }

        producto.getTickets().add(ticketNuevo);
        ticketNuevo.setProducto(producto);

        return Converter.convertToTicketResponse(ticketRepo.save(ticketNuevo));
    }

    public TicketResponse actualizarTicket(Long id, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage()));

        Optional.ofNullable(ticketRequest.getNombre()).ifPresent(ticket::setNombre);
        Optional.ofNullable(ticketRequest.getDescripcion()).ifPresent(ticket::setDescripcion);

        if (!ticketRequest.getPrioridad().isBlank()) {
            Validacion.validarEnum(ticketRequest.getPrioridad(), Prioridad.class);
            ticket.setPrioridad(Prioridad.valueOf(ticketRequest.getPrioridad().toUpperCase()));
        }

        if (!ticketRequest.getSeveridad().isBlank()) {
            Validacion.validarEnum(ticketRequest.getSeveridad(), Severidad.class);
            ticket.setSeveridad(Severidad.valueOf(ticketRequest.getSeveridad().toUpperCase()));
        }

        if (!ticketRequest.getCategoria().isBlank()) {
            Validacion.validarEnum(ticketRequest.getCategoria(), Categoria.class);
            ticket.setCategoria(Categoria.valueOf(ticketRequest.getCategoria().toUpperCase()));

        }

        if (!ticketRequest.getEstado().isBlank()) {
            Validacion.validarEnum(ticketRequest.getEstado(), Estado.class);
            ticket.setEstado(Estado.valueOf(ticketRequest.getEstado().toUpperCase()));
        }

        if (!ticketRequest.getCliente_id().equals(0L)){
            Cliente cliente = clienteService.getClienteById(ticketRequest.getCliente_id());
            ticket.setCliente(cliente);
        }
        if (!ticketRequest.getColaborador_id().equals(0L)){
            Colaborador colaborador = colaboradorService.getColaboradorById(ticketRequest.getColaborador_id());
            ticket.setColaborador(colaborador);
        }

        return Converter.convertToTicketResponse(ticketRepo.save(ticket));
    }


    public void quitarTicket(Long id) {

        ticketRepo.deleteById(id);
    }




}
