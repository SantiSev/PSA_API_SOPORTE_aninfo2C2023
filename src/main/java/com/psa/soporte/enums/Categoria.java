package com.psa.soporte.enums;

import lombok.Getter;

@Getter
public enum Categoria {
    PROYECTO("Proyecto"),
    SOPORTE("Soporte");

    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

}
