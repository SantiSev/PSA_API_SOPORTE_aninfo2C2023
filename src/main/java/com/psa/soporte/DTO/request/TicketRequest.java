package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketRequest {

    @Schema(example = "Nombre del ticket ejemplo")
    private String nombre;
    @Schema(example = "Descripcion del ticket ejemplo")
    private String descripcion;
    private String prioridad;
    private String severidad;
    private String categoria;
    private String estado;
    private Long tarea_id;
    private Long cliente_id;
    private Long colaborador_id;
}
