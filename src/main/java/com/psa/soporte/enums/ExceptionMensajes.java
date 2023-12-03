package com.psa.soporte.enums;

public enum ExceptionMensajes {
    EMPTY_LIST("La lista está vacía"),
    TICKET_NOT_FOUND("Ticket con este id no existe"),
    COLABORADOR_NOT_FOUND("Colaborador con este id no existe"),

    PRODUCTO_NOT_FOUND("Producto con este id no existe"),
    PRODUCTO_VERSION_NOT_FOUND("Esta version de producto con este id no existe"),
    CLIENTE_NOT_FOUND("Cliente con este id no existe"),
    COLABORADOR_YA_EXISTE("Ya existe un colaborador con ese legajo ingresado"),
    CLIENTE_YA_EXISTE("Ya existe un cliente con ese nombre ingresado"),
    ERROR_PROCESAR_CLIENTES("Hubo un error con el URL que has ingresado, por favor reviselo e intentalo denuevo"),
    PRODUCTO_VERSION_YA_EXISTE("Ya existe una version de este prodcuto!"), TAREA_NOT_FOUND("No existe una tarea con el ID ingresado");

    private final String message;

    ExceptionMensajes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
