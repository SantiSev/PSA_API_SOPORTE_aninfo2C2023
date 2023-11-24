package com.psa.soporte.DTO.request;


import com.psa.soporte.enums.Categoria;
import com.psa.soporte.enums.Estado;
import com.psa.soporte.enums.Prioridad;
import com.psa.soporte.enums.Severidad;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketRequest {

    private String nombre;
    private String descripcion;
    private Prioridad prioridad;
    private Severidad severidad;
    private Categoria categoria;
    private Estado estado;

}
