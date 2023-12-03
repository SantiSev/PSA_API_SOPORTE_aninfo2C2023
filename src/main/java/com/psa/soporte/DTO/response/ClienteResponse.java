package com.psa.soporte.DTO.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClienteResponse {
    private Long clientId;
    private String razonSocial;
    private String CUIT;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
