package com.example.Foro_Hub.domain.respuesta;


import java.time.LocalDateTime;

public record ListarRespuestasDTO(
        Long id,
        String solucion,
        Long autorId,
        Long topicoId,
        LocalDateTime fechaCreacion
) {
    public ListarRespuestasDTO(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getSolucion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion()
        );
    }

}