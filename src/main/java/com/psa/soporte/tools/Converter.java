package com.psa.soporte.tools;

import com.psa.soporte.DTO.response.*;
import com.psa.soporte.modelos.*;

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
            response.setColaboradorId(ticket.getColaborador().getColaboradorId());
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

    public static ColaboradorResponse convertToColaboradorResponse(Colaborador colaborador){
        ColaboradorResponse response = new ColaboradorResponse();
        response.setColaboradorId(colaborador.getColaboradorId());
        response.setNombre(colaborador.getNombre());
        response.setLegajo(colaborador.getLegajo());
        response.setCreatedAt(colaborador.getCreatedAt());
        response.setUpdatedAt(colaborador.getUpdatedAt());
        return response;
    }

    public static ClienteResponse convertToClienteResponse(Cliente cliente){
        ClienteResponse response = new ClienteResponse();
        response.setClientId(cliente.getClientId());
        response.setRazonSocial(cliente.getRazonSocial());
        response.setCUIT(cliente.getCUIT());
        response.setCreatedAt(cliente.getCreatedAt());
        response.setUpdatedAt(cliente.getUpdatedAt());
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

    public static List<ClienteResponse> convertToClienteResponseList(List<Cliente> clientes) {
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteResponse response = convertToClienteResponse(cliente);
            clienteResponses.add(response);
        }
        return clienteResponses;
    }

    public static List<ColaboradorResponse> convertToColaboradorResponseList(List<Colaborador> colaboradores) {
        List<ColaboradorResponse> colaboradorResponses = new ArrayList<>();
        for (Colaborador colaborador : colaboradores) {
            ColaboradorResponse response = convertToColaboradorResponse(colaborador);
            colaboradorResponses.add(response);
        }
        return colaboradorResponses;
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
