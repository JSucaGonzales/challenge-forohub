package com.example.Foro_Hub.domain.topico;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroTopicoDTO (
        @NotBlank(message = "Título es obligatorio")
        String titulo,
        @NotBlank(message = "Mensaje es obligatorio")
        String mensaje,
        @NotBlank(message = "Curso es obligatorio")
        String nombreCurso,
        @NotNull(message = "Author_id es obligatorio")
        Long autorId
) {
    // no necesario
//    public RegistroTopicoDTO(String titulo, String mensaje, String nombreCurso, Long autorId) {
//        this.titulo = titulo;
//        this.mensaje = mensaje;
//        this.nombreCurso = nombreCurso;
//        this.autorId = autorId;
    //}
}