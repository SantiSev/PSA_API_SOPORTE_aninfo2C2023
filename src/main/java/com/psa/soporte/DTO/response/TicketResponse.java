package com.psa.soporte.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponse {
    private Long ticket_id;
    private Long producto_id;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private String severidad;
    private String categoria;
    private String estado;
    private Long tarea_id;
    private Long cliente_id;
    private Long colaborador_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
