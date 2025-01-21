package com.example.Foro_Hub.domain.topico;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TopicoActualizadoDTO(
        @NotNull Long id,
        String titulo,
        String mensaje,
        Status status,
        @NotNull
        Long autor_id,
        String nombreCurso,
        LocalDateTime fecha,
        boolean activo
) {
}