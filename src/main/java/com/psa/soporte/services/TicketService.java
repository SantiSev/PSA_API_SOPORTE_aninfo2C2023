package com.psa.soporte.services;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.DTO.response.TareaResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.*;
import com.psa.soporte.modelos.*;
import com.psa.soporte.repo.ProductoRepo;
import com.psa.soporte.repo.ProductoVersionRepo;
import com.psa.soporte.repo.TicketRepo;
import com.psa.soporte.tools.Converter;
import com.psa.soporte.tools.FetchResources;
import com.psa.soporte.tools.Validacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepo ticketRepo;
    private final ProductoRepo productoRepo;
    private final ProductoVersionRepo productoVersionRepo;
    private final ClienteService clienteService;
    private final ColaboradorService colaboradorService;

    @Autowired
    public TicketService(TicketRepo ticketRepo, ProductoRepo productoRepo, ProductoVersionRepo productoVersionRepo, ClienteService clienteService, ColaboradorService colaboradorService) {
        this.ticketRepo = ticketRepo;
        this.productoRepo = productoRepo;
        this.productoVersionRepo = productoVersionRepo;
        this.clienteService = clienteService;
        this.colaboradorService = colaboradorService;
    }


    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = (List<Ticket>) ticketRepo.findAll();
        if (tickets.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(tickets);
    }

    public List<TicketResponse> getAllTicketsByProducto(Long productoId) {

        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        List<Ticket> tickets = producto.getVersiones().stream()
                .flatMap(version -> version.getTickets().stream())
                .collect(Collectors.toList());

        if (tickets.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }

        return Converter.convertToTicketResponseList(tickets);
    }

    public TicketResponse getTicketById(Long id) {
        return Converter.convertToTicketResponse(ticketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage())));
    }

    public TicketResponse crearTicket(TicketRequest ticketRequest, Long ProductoVersionId) {

        ProductoVersion version = productoVersionRepo.findById(ProductoVersionId)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

        ticketSinTitulo(ticketRequest);

        Ticket ticketNuevo = new Ticket(ticketRequest);

        if (!(ticketRequest.getClienteId() == 0)){
            Cliente cliente = clienteService.getClienteById(Long.valueOf(ticketRequest.getClienteId()));
            ticketNuevo.setCliente(cliente);
        }
        if (!(ticketRequest.getColaboradorId() == 0)){
            Colaborador colaborador = colaboradorService.getColaboradorById(Long.valueOf(ticketRequest.getColaboradorId()));
            ticketNuevo.setColaborador(colaborador);
        }

        version.getTickets().add(ticketNuevo);
        ticketNuevo.setProductoVersion(version);

        ticketRepo.save(ticketNuevo);
        FetchResources.setTicketTarea(ticketNuevo, ticketRequest.getTareaIds());

        return Converter.convertToTicketResponse(ticketNuevo);
    }

    public TicketResponse actualizarTicket(Long id, TicketRequest ticketRequest) {

        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage()));

        ticketSinTitulo(ticketRequest);

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

        if (!(ticketRequest.getClienteId() == 0)){
            Cliente cliente = clienteService.getClienteById(Long.valueOf(ticketRequest.getClienteId()));
            ticket.setCliente(cliente);
        }else {
            ticket.setCliente(null);
        }

        if (!(ticketRequest.getColaboradorId() == 0)){
            Colaborador colaborador = colaboradorService.getColaboradorById(Long.valueOf(ticketRequest.getColaboradorId()));
            ticket.setColaborador(colaborador);
        }else {
            ticket.setCliente(null);
        }

        FetchResources.setTicketTarea(ticket, ticketRequest.getTareaIds());

        return Converter.convertToTicketResponse(ticketRepo.save(ticket));
    }

    public void quitarTicket(Long id){
        ticketRepo.deleteById(id);
    }

    private void ticketSinTitulo(TicketRequest request){
        if (request.getNombre().isBlank() || request.getNombre().isEmpty()){
            request.setNombre("(sin titulo)");
        }
    }



}
