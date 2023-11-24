package com.psa.soporte.enums;

import lombok.Getter;

@Getter
public enum Prioridad {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    private final String descripcion;

    Prioridad(String descripcion) {
        this.descripcion = descripcion;
    }
}
