package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ColaboradorRequest {

    @Schema(example = "Santiago Sev")
    private String nombre;

    @Schema(example = "123")
    private Integer legajo;

}
