package com.psa.soporte.tools;

public class Validacion {


    public static <T extends Enum<T>> void validarEnum(String valor, Class<T> enumType) {
        if (valor != null) {
            try {
                Enum.valueOf(enumType, valor.toUpperCase()); // Convertir el string a mayúsculas antes de verificar
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(enumType.getSimpleName() + " Error: El valor ingresado '" + valor + "' no es válido");
            }
        }else {
            throw new IllegalArgumentException(enumType.getSimpleName() + " Error: Este campo no puede quedar vacio");
        }
    }
}
