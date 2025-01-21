package com.example.Foro_Hub.domain.usuario;

import jakarta.validation.constraints.NotNull;


public record ActualizacionUsuarioDTO(

        @NotNull
        Long id,
        String nombre,
        String email,
        boolean activo,
        String username,
        String clave
) {}


