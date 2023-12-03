package com.psa.soporte.DTO.response;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ColaboradorResponse {
    private Long colaboradorId;
    private String nombre;
    private Long legajo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
