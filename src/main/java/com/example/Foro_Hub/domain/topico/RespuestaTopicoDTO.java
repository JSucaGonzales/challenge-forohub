package com.example.Foro_Hub.domain.topico;

import com.example.Foro_Hub.domain.usuario.Usuario;
import java.time.LocalDateTime;

public record RespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        Status status,
        Usuario autor,
        String nombreCurso,
        LocalDateTime fecha) {

    public RespuestaTopicoDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getNombreCurso(),
                topico.getFecha()
        );
    }
}
