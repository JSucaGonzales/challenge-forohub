package com.example.Foro_Hub.domain.usuario;

import com.example.Foro_Hub.domain.usuario.UsuarioService;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marca esta clase como un componente de servicio en Spring, para la lógica de negocio.
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    // Repositorio para realizar operaciones CRUD relacionadas con usuarios en la base de datos.

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    // Codificador para manejar contraseñas de manera segura.

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param registroUsuarioDTO Contiene los datos del usuario que se desea registrar.
     * @return UsuarioDTO con los datos del usuario registrado.
     */
    public UsuarioDTO registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        validarUnicidadUsuario(registroUsuarioDTO); // Verifica que el email y el nombre de usuario sean únicos.

        // Codificar la contraseña antes de almacenarla.
        String encodedPassword = passwordEncoder.encode(registroUsuarioDTO.clave());
        Usuario usuario = new Usuario(
                registroUsuarioDTO.nombre(),  // Nombre del usuario.
                registroUsuarioDTO.username(), // Nombre de usuario.
                registroUsuarioDTO.email(),    // Correo electrónico.
                encodedPassword,               // Contraseña codificada.
                true                           // Usuario activo por defecto.
        );

        usuarioRepository.save(usuario); // Guarda el usuario en la base de datos.

        // Retorna un DTO con los datos del usuario registrado.
        return new UsuarioDTO(
                usuario.getId(),        // ID del usuario.
                usuario.getNombre(),    // Nombre del usuario.
                usuario.getEmail(),     // Correo electrónico.
                usuario.getUsername(),  // Nombre de usuario.
                encodedPassword         // Contraseña codificada.
        );
    }

    /**
     * Valida que el correo electrónico y el nombre de usuario sean únicos.
     * @param registroUsuarioDTO Datos del usuario que se desea registrar.
     */
    private void validarUnicidadUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        if (usuarioRepository.existsByEmail(registroUsuarioDTO.email())) {
            throw new ValidacionDeIntegridad("Este correo electrónico ya está registrado.");
        }
        if (usuarioRepository.existsByUsername(registroUsuarioDTO.username())) {
            throw new ValidacionDeIntegridad("Este nombre de usuario ya está en uso.");
        }
    }

    /**
     * Desactiva un usuario (eliminación lógica).
     * @param id ID del usuario que se desea desactivar.
     * @return RegistroUsuarioDTO con los datos del usuario desactivado.
     */
    public RegistroUsuarioDTO desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));

        usuario.setActivo(false); // Cambia el estado del usuario a inactivo.
        usuarioRepository.save(usuario); // Guarda los cambios en la base de datos.

        // Retorna un DTO con los datos del usuario desactivado.
        return new RegistroUsuarioDTO(
                usuario.getId(),          // ID del usuario.
                usuario.getNombre(),      // Nombre del usuario.
                usuario.getUsername(),    // Nombre de usuario.
                usuario.getEmail(),       // Correo electrónico.
                usuario.getPassword()     // Contraseña codificada.
        );
    }
}

