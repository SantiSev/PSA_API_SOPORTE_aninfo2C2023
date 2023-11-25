package com.psa.soporte.enums;

import lombok.Getter;

@Getter
public enum Categoria {
    PROYECTO("Proyecto"),
    SOPORTE("Soporte"),
    FINANZAS("Finanzas"),
    IT("IT");

    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

}
