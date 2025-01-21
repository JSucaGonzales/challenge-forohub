package com.example.Foro_Hub.domain.respuesta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record RespuestaDTO(
        @NotBlank
        String solucion,
        @NotNull
        @Valid
        Long autorId,
        @NotNull
        @Valid
        Long topicoId,
        boolean activo,
        LocalDateTime fechaCreacion) {

    public RespuestaDTO {
    }
}
