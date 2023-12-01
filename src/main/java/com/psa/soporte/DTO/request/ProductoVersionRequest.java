package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductoVersionRequest {

    @Schema(example = "1.0.0")
    private String version;

}
