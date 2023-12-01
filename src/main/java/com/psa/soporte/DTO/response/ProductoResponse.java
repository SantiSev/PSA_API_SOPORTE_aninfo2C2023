package com.psa.soporte.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductoResponse {

    private Long productoId;
    private String nombre;
    private List<ProductoVersionResponse> versiones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
