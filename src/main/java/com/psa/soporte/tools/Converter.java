package com.psa.soporte.tools;

import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.modelos.Cliente;
import com.psa.soporte.modelos.Producto;
import com.psa.soporte.modelos.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    public static TicketResponse convertToTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setTicket_id(ticket.getTicket_id());
        response.setProducto_id(ticket.getProducto().getProducto_id());
        response.setNombre(ticket.getNombre());
        response.setDescripcion(ticket.getDescripcion());
        response.setPrioridad(ticket.getPrioridad().toString());
        response.setSeveridad(ticket.getSeveridad().toString());
        response.setCategoria(ticket.getCategoria().toString());
        response.setEstado(ticket.getEstado().toString());
        response.setTarea_id(ticket.getTarea_id());

        if (ticket.getCliente() != null){
            response.setCliente_id(ticket.getCliente().getClient_id());
        }
        if (ticket.getColaborador() != null){
            response.setColaborador_id(response.getColaborador_id());
        }

        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());

        return response;
    }

    public static ProductoResponse convertToProductoResponse(Producto producto) {

        ProductoResponse response = new ProductoResponse();
        response.setProducto_id(producto.getProducto_id());
        response.setVersion(producto.getVersion());
        response.setProyecto_id(producto.getProyecto_id());


        response.setTicket_ids(producto.getTickets().stream()
                .map(Ticket::getTicket_id)
                .collect(Collectors.toList()));

        response.setCreatedAt(producto.getCreatedAt());
        response.setUpdatedAt(producto.getUpdatedAt());

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

    public static List<ProductoResponse> convertToProductoResponseList(List<Producto> productos) {
        List<ProductoResponse> productoResponses = new ArrayList<>();
        for (Producto producto : productos) {
            ProductoResponse response = convertToProductoResponse(producto);
            productoResponses.add(response);
        }
        return productoResponses;
    }

}
