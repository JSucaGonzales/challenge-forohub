package com.example.Foro_Hub.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {

    public ValidacionDeIntegridad(String message) {
        super(message);
    }

    public String getErrorCode() {
        return "VALIDACION_INTEGRIDAD_ERROR";
    }
}