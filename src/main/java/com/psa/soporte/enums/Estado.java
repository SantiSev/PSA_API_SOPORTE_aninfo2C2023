package com.psa.soporte.enums;

import lombok.Getter;

@Getter
public enum Estado {

    SIN_INICIAR("Sin iniciar"),
    EN_PROGRESO("En Progreso"),
    EN_DESARROLLO("En Desarrollo"),
    EN_IMPLEMENTACION("En Implementación"),
    ESPERANDO_AL_CLIENTE("Esperando Al Cliente"),
    BLOQUEADO("Bloqueado"),
    RESUELTO("Resuelto"),
    CERRADO("Cerrado");

    private final String estado;

    Estado(String estado) {
        this.estado = estado;
    }
}
