package com.example.Foro_Hub.domain.topico;

import java.time.LocalDateTime;

public record ListarTopicosDTO(
        Long id,
        String titulo,
        String mensaje,
        Status status,
        Long autorId,
        String nombreCurso,
        LocalDateTime fecha) {

    public ListarTopicosDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getNombreCurso(),
                topico.getFecha()
        );
    }
}
