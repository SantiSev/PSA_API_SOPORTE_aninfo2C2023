package com.psa.soporte.tools;

import com.psa.soporte.DTO.response.ProductoResponse;
import com.psa.soporte.DTO.response.ProductoVersionResponse;
import com.psa.soporte.DTO.response.TareaResponse;
import com.psa.soporte.DTO.response.TicketResponse;
import com.psa.soporte.modelos.Producto;
import com.psa.soporte.modelos.ProductoVersion;
import com.psa.soporte.modelos.Tarea;
import com.psa.soporte.modelos.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    public static TicketResponse convertToTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setTicketId(ticket.getTicketId());
        response.setVersionNombre(ticket.getProductoVersion().getVersion());
        response.setNombre(ticket.getNombre());
        response.setDescripcion(ticket.getDescripcion());
        response.setPrioridad(ticket.getPrioridad().toString());
        response.setSeveridad(ticket.getSeveridad().toString());
        response.setCategoria(ticket.getCategoria().toString());
        response.setEstado(ticket.getEstado().toString());

        response.setTareaIds(ticket.getTareas().stream()
                .map(Tarea::getTareaIdRemoto)
                .collect(Collectors.toList()));

        if (ticket.getCliente() != null){
            response.setClienteId(ticket.getCliente().getClientId());
        }
        if (ticket.getColaborador() != null){
            response.setColaboradorId(response.getColaboradorId());
        }

        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());

        return response;
    }

    public static ProductoResponse convertToProductoResponse(Producto producto) {

        ProductoResponse response = new ProductoResponse();
        response.setProductoId(producto.getProductoId());
        response.setNombre(producto.getNombre());
        response.setVersiones(convertToProductoVersionResponseList(producto.getVersiones()));
        response.setCreatedAt(producto.getCreatedAt());
        response.setUpdatedAt(producto.getUpdatedAt());

        return response;
    }

    public static ProductoVersionResponse convertToProductoVersionResponse(ProductoVersion productoVersion) {

        ProductoVersionResponse response = new ProductoVersionResponse();
        response.setProductoVersionId(productoVersion.getProductoVersionId());
        response.setVersion(productoVersion.getVersion());
        response.setCreatedAt(productoVersion.getCreatedAt());
        response.setUpdatedAt(productoVersion.getUpdatedAt());
        return response;
    }

    public static TareaResponse convertToTareaResponse(Tarea tarea){

        TareaResponse response = new TareaResponse();
        response.setTareaId(tarea.getTareaId());
        response.setTareaIdRemoto(tarea.getTareaIdRemoto());
        response.setTicketIds(tarea.getTickets().stream()
                .map(Ticket::getTicketId)
                .collect(Collectors.toList()));
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

    public static List<ProductoVersionResponse> convertToProductoVersionResponseList(List<ProductoVersion> versiones) {
        List<ProductoVersionResponse> responses = new ArrayList<>();
        for (ProductoVersion version : versiones) {
            ProductoVersionResponse response = convertToProductoVersionResponse(version);
            responses.add(response);
        }
        return responses;
    }

}
