package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketRequest {

    @Schema(example = "Nombre del ticket ejemplo")
    private String nombre;
    @Schema(example = "Descripcion del ticket ejemplo")
    private String descripcion;
    @Schema(example = "Media")
    private String prioridad;
    @Schema(example = "S1")
    private String severidad;
    @Schema(example = "Proyecto")
    private String categoria;
    @Schema(example = "SIN_INICIAR")
    private String estado;
    @Schema(example = "0")
    private Integer clienteId;
    @Schema(example = "0")
    private Integer colaboradorId;
    private TareaRequest tareaRequest;
}
