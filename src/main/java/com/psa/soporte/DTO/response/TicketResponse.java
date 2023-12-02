package com.psa.soporte.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketResponse {
    private Long ticketId;
    private String versionNombre;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private String severidad;
    private String categoria;
    private String estado;
    private Long clienteId;
    private Long colaboradorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> tareaIds;
}
