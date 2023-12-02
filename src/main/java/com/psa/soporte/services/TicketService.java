package com.psa.soporte.services;

import com.psa.soporte.DTO.request.TicketRequest;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.enums.*;
import com.psa.soporte.modelos.*;
import com.psa.soporte.repo.ProductoVersionRepo;
import com.psa.soporte.repo.TareaRepo;
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
    private final TareaRepo tareaTicketRepo;
    private final ProductoVersionRepo productoVersionRepo;
    private final ClienteService clienteService;
    private final ColaboradorService colaboradorService;

    @Autowired
    public TicketService(TicketRepo ticketRepo, TareaRepo tareaTicketRepo, ClienteService clienteService, ColaboradorService colaboradorService, ProductoVersionRepo productoVersionRepo) {
        this.ticketRepo = ticketRepo;
        this.tareaTicketRepo = tareaTicketRepo;
        this.clienteService = clienteService;
        this.colaboradorService = colaboradorService;
        this.productoVersionRepo = productoVersionRepo;
    }


    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = (List<Ticket>) ticketRepo.findAll();
        if (tickets.isEmpty()) {
            throw new NotFoundException(ExceptionMensajes.EMPTY_LIST.getMessage());
        }
        return Converter.convertToTicketResponseList(tickets);
    }

    public List<TicketResponse> getAllTicketsByVersion(Long productoVersionId) {
        
        List<Ticket> tickets = productoVersionRepo.findById(productoVersionId)
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

    public TicketResponse crearTicket(TicketRequest ticketRequest, Long ProductoVersionId) {

        ProductoVersion version = productoVersionRepo.findById(ProductoVersionId)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.PRODUCTO_NOT_FOUND.getMessage()));

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

        for (Long tareaId: ticketRequest.getTareaRequest().getTareaId()) {
            Tarea tarea = tareaTicketRepo.findById(tareaId).orElseGet(() -> new Tarea(tareaId));

            tarea.getTickets().add(ticketNuevo);
            ticketNuevo.getTareas().add(tarea);

            tareaTicketRepo.save(tarea);
        }

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

        return Converter.convertToTicketResponse(ticketRepo.save(ticket));
    }


    public TicketResponse agregarTarea(Long ticketId, Long tareaId){
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage()));

        Tarea tarea = tareaTicketRepo.findById(tareaId).orElseGet(() -> new Tarea(tareaId));

        boolean tareaYaAsociada = ticket.getTareas().stream()
                .anyMatch(t -> t.getTareaIdRemoto().equals(tareaId));

        if (!tareaYaAsociada) {
            ticket.getTareas().add(tarea);
            tarea.getTickets().add(ticket);

            tareaTicketRepo.save(tarea);
        }

        tareaTicketRepo.save(tarea);

        return Converter.convertToTicketResponse(ticketRepo.save(ticket));
    }

    public TicketResponse quitarTarea(Long ticketId, Long tareaId){

        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new NotFoundException(ExceptionMensajes.TICKET_NOT_FOUND.getMessage()));

        for (Tarea tarea: ticket.getTareas()) {
            if (tarea.getTareaId().equals(tareaId)){
                ticket.getTareas().remove(tarea);
                tarea.getTickets().remove(ticket);
                tareaTicketRepo.save(tarea);
                break;
            }
        }
        return Converter.convertToTicketResponse(ticketRepo.save(ticket));
    }

    public void quitarTicket(Long id) {
        ticketRepo.deleteById(id);
    }

    public void quitarTarea(Long id){
        tareaTicketRepo.deleteById(id);
    }




}
