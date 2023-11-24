package com.psa.soporte.enums;

import lombok.Getter;

@Getter
public enum Severidad {
    S1("Muy grave"),
    S2("Menos grave"),
    S3("No está claro"),
    S4("Consulta");

    private final String descripcion;

    Severidad(String descripcion) {
        this.descripcion = descripcion;
    }

}
