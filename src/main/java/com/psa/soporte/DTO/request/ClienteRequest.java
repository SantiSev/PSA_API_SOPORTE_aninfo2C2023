package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClienteRequest {
    @Schema(example = "S.E.V")
    private String razonSocial;
    @Schema(example = "20-42955-41")
    private String CUIT;
}
