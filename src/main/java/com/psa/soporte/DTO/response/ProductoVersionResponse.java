package com.psa.soporte.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductoVersionResponse {

    private Long productoVersionId;
    private String version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
