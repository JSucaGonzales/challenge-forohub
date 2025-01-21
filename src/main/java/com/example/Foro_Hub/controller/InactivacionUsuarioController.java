package com.example.Foro_Hub.controller;


import com.example.Foro_Hub.domain.usuario.UsuarioService;
import com.example.Foro_Hub.domain.usuario.RegistroUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario/usuarios")
public class InactivacionUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/desactivar/{id}")
    public RegistroUsuarioDTO desactivarUsuario(@PathVariable Long id) {
        return usuarioService.desactivarUsuario(id);
    }
}