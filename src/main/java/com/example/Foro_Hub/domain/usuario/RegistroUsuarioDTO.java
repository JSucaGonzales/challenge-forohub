package com.example.Foro_Hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistroUsuarioDTO(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotBlank(message = "Utilice su correo electrónico como nombre de usuario")
        @Email(message = "El correo electrónico es inválido.")
        String username,

        @NotBlank(message = "El correo electrónico no puede estar vacío")
        @Email(message = "El correo electrónico es inválido.")
        String email,

        @NotBlank(message = "Debe tener entre 6 y 10 caracteres.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$", message = "La contraseña debe tener entre 6 y 10 caracteres, incluyendo letras y números.")
        String clave
) {@Override
public Long id() {
        return id;
}

        @Override
        public String nombre() {
                return nombre;
        }

        @Override
        public String username() {
                return username;
        }

        @Override
        public String email() {
                return email;
        }

        @Override
        public String clave() {
                return clave;
        }}
