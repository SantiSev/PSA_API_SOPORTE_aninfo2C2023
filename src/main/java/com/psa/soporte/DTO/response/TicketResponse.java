package com.psa.soporte.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketResponse {
    private Long ticket_id;
    private String nombre;
    private String descripcion;
    private String prioridad;
    private String severidad;
    private String categoria;
    private String estado;
    private Long tarea_id;
    private Long cliente_id;
}
