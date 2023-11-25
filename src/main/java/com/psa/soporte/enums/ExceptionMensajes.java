package com.psa.soporte.enums;

public enum ExceptionMensajes {
    EMPTY_LIST("La lista está vacía"),
    TICKET_NOT_FOUND("Ticket con este id no existe"),
    COLABORADOR_NOT_FOUND("Colaborador con este id no existe"),
    CLIENTE_NOT_FOUND("Cliente con este id no existe"),
    COLABORADOR_YA_EXISTE("Ya existe un colaborador con ese legajo ingresado"),
    CLIENTE_YA_EXISTE("Ya existe un cliente con ese nombre ingresado");

    private final String message;

    ExceptionMensajes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
