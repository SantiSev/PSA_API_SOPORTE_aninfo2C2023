package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductoRequest {

    @Schema(example = "Sistema de Gestion")
    private String nombre;

}
