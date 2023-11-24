package com.psa.soporte.DTO.request;


import com.psa.soporte.enums.Estado;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TareaRequest {

    private Long proyecto_id;
    private String nombre;
    private String nombre_asignado;
    private String descripcion;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_cierre;
    private Estado estado;

}
