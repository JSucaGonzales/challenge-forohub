package com.example.Foro_Hub.controller;

import com.example.Foro_Hub.domain.usuario.UsuarioService;
import com.example.Foro_Hub.domain.usuario.*;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import com.example.Foro_Hub.infra.security.JWTTokenDTO;
import com.example.Foro_Hub.infra.security.TokenService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario") // Ruta base para gestionar usuarios.
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    // Repositorio para realizar operaciones CRUD relacionadas con usuarios.

    @Autowired
    private UsuarioService usuarioService;
    // Servicio que contiene la lógica de negocio para usuarios.

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    // Codificador para manejar contraseñas de manera segura.

    @Autowired
    private TokenService tokenService;
    // Servicio para generar y validar tokens JWT.

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO registroUsuarioDTO, UriComponentsBuilder uriComponentsBuilder) {
        // Registra un nuevo usuario en la base de datos.
        try {
            UsuarioDTO usuarioDTO = usuarioService.registrarUsuario(registroUsuarioDTO);
            // Crea una respuesta con los detalles del usuario registrado.
            RespuestaUsuarioDTO respuesta = new RespuestaUsuarioDTO(usuarioDTO.id(), usuarioDTO.nombre());
            URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuarioDTO.id()).toUri();
            return ResponseEntity.created(url).body(respuesta); // Responde con un código HTTP 201 Created.
        } catch (ConstraintViolationException ex) {
            // Manejo de excepciones de validación.
            return ResponseEntity.badRequest().body("Validation failed: " + ex.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Page<ListarUsuariosDTO>> listarUsuarios(@PageableDefault(size = 10) Pageable paged) {
        // Lista todos los usuarios activos con paginación.
        return ResponseEntity.ok(usuarioRepository.findByActivoTrue(paged).map(ListarUsuariosDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ActualizacionUsuarioDTO> actualizacionUsuario(@PathVariable Long id,
                                                                        @RequestBody @Valid ActualizacionUsuarioDTO actualizacionUsuarioDTO) {
        // Actualiza los datos de un usuario específico.
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));

        usuario.actualizacionUsuario(actualizacionUsuarioDTO, bCryptPasswordEncoder);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(
                new ActualizacionUsuarioDTO(
                        usuario.getId(),          // ID del usuario.
                        usuario.getNombre(),      // Nombre del usuario.
                        usuario.getEmail(),       // Email del usuario.
                        usuario.isActivo(),       // Estado de actividad.
                        usuario.getUsername(),    // Nombre de usuario.
                        usuario.getPassword()     // Contraseña encriptada.
                )
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        // Desactiva (elimina lógicamente) un usuario por su ID.
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.deactivateUser(); // Cambia el estado del usuario a inactivo.
        return ResponseEntity.noContent().build(); // Responde con un código HTTP 204 (sin contenido).
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        // Obtiene un usuario por su ID y devuelve sus detalles.
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var usuarioDetail = new RespuestaUsuarioDTO(usuario.getId(), usuario.getNombre());
        return ResponseEntity.ok(usuarioDetail); // Respuesta con los datos del usuario.
    }

    @RequestMapping("/usuarios")
    public class InactivacionUsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @PostMapping("/desactivar/{id}")
        public RegistroUsuarioDTO desactivarUsuario(@PathVariable Long id) {
            // Desactiva un usuario utilizando el servicio correspondiente.
            return usuarioService.desactivarUsuario(id);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuarioDTO loginUsuarioDTO) {
        try {
            // Log para verificar el email ingresado.
            System.out.println("Email ingresado: " + loginUsuarioDTO.getEmail());

            // Busca el usuario por su email.
            Usuario usuario = usuarioRepository.findByEmail(loginUsuarioDTO.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            // Log para verificar la contraseña almacenada.
            System.out.println("Contraseña almacenada: " + usuario.getPassword());

            // Valida la contraseña proporcionada.
            if (!bCryptPasswordEncoder.matches(loginUsuarioDTO.getPassword(), usuario.getPassword())) {
                System.out.println("Contraseña ingresada: " + loginUsuarioDTO.getPassword());
                return ResponseEntity.status(401).body("Credenciales incorrectas"); // Responde con un código HTTP 401.
            }

            // Genera el token JWT para el usuario autenticado.
            String token = tokenService.generarToken(usuario);
            return ResponseEntity.ok(new JWTTokenDTO(token)); // Devuelve el token en la respuesta.

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error de servidor: " + e.getMessage()); // Manejo de errores internos.
        }
    }
}

