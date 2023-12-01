package com.psa.soporte.DTO.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TareaRequest {

    @Schema(example = "[1,2,3,4,5]")
    private List<Long> tareaId;
}
