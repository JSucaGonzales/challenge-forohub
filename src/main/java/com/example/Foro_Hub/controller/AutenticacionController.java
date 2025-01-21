package com.example.Foro_Hub.controller;

import com.example.Foro_Hub.domain.usuario.Usuario;
import com.example.Foro_Hub.infra.security.JWTTokenDTO;
import com.example.Foro_Hub.infra.security.TokenService;
import com.example.Foro_Hub.domain.usuario.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login") // Define el endpoint base para las operaciones de autenticación.
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                usuarioDTO.email(),
                usuarioDTO.clave()
        );

        var autenticacionUsuario = authenticationManager.authenticate(authToken);
        var token = tokenService.generarToken((Usuario) autenticacionUsuario.getPrincipal());

        return ResponseEntity.ok(new JWTTokenDTO(token));
    }
}
