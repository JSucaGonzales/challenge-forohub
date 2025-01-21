package com.example.Foro_Hub.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Foro_Hub.domain.usuario.Usuario;
import com.example.Foro_Hub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(usuario.getPassword());
            return JWT.create()
                    .withIssuer("foro hub")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(expirationdate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token", exception);
        }
    }

    public String getSubject(String token){
        if (token == null) {
            throw new IllegalArgumentException("El token es nulo.");
        }
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String username = decodedJWT.getSubject();
            if (username == null) {
                throw new IllegalArgumentException("Token no válido: Asunto no encontrado");
            }
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
            if (usuario.isEmpty()) {
                throw new IllegalArgumentException("Usuario no encontrado por nombre de usuario: " + username);
            }

            Algorithm algorithm = Algorithm.HMAC256(usuario.get().getPassword());
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("foro hub")
                    .build()
                    .verify(token);

            return verifier.getSubject();
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Token no válido: " + e.getMessage(), e);
        }
    }

    private Instant expirationdate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
