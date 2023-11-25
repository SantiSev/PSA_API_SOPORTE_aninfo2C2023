package com.psa.soporte.tools;

import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.modelos.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static TicketResponse convertToTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setTicket_id(ticket.getTicket_id());
        response.setNombre(ticket.getNombre());
        response.setDescripcion(ticket.getDescripcion());
        response.setPrioridad(ticket.getPrioridad().toString());
        response.setSeveridad(ticket.getSeveridad().toString());
        response.setCategoria(ticket.getCategoria().toString());
        response.setEstado(ticket.getEstado().toString());
        response.setTarea_id(ticket.getTarea_id());
        response.setCliente_id(ticket.getCliente().getClient_id());
        return response;
    }

    public static List<TicketResponse> convertToTicketResponseList(List<Ticket> tickets) {
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketResponse response = convertToTicketResponse(ticket);
            ticketResponses.add(response);
        }
        return ticketResponses;
    }


}
