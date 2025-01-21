package com.example.Foro_Hub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RespuestaActualizadaDTO(
        @NotNull Long id,
        String solucion,
        @NotNull Long autorId,
        @NotNull Long topicoId,
        LocalDateTime fechaCreacion
) {
}