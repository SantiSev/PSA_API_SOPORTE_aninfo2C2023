package com.psa.soporte.DTO.request;


import com.psa.soporte.enums.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ColaboradorRequest {

    @Schema(example = "Santiago Sev")
    private String nombre;

    @Schema(example = "123")
    private Long legajo;

}
