package com.example.Foro_Hub.domain.usuario;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
        Long id,  // Agrega el id aquí
        String nombre,
        @NotBlank(message = "Utilice su correo electrónico como nombre de usuario")
        @Email(message = "El formato del correo electrónico es inválido.")
        String email,
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        @Pattern(regexp = "^[a-zA-Z0-9._-]{5,20}$", message = "El nombre de usuario debe tener entre 5 y 20 caracteres y solo puede contener letras, números, puntos, guiones bajos y guiones.")
        String username,
        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 10, max = 15, message = "La contraseña debe tener entre 10 y 15 caracteres.")
        String clave
) {

}