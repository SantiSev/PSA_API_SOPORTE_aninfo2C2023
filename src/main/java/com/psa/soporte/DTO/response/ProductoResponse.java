package com.psa.soporte.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductoResponse {

    private Long producto_id;
    private Long proyecto_id;
    private String version;
    private List<Long> ticket_ids;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
