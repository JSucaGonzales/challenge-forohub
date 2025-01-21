package com.example.Foro_Hub.domain.respuesta;

import com.example.Foro_Hub.domain.topico.TopicoRepository;
import com.example.Foro_Hub.domain.usuario.UsuarioRepository;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RespuestaRepository repository;

    public RespuestaCreadaDTO respuestaCreadaDTO(RespuestaDTO respuestaDTO) {
        if (!usuarioRepository.findById(respuestaDTO.autorId()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID de usuario no está registrado en la base de datos. ");
        }
        if (!topicoRepository.findById(respuestaDTO.topicoId()).isPresent()){
            throw new ValidacionDeIntegridad("Este id de tópico no está registrado en la base de datos. ");
        }
        var usuario = usuarioRepository.findById(respuestaDTO.autorId()).get();
        var topico = topicoRepository.findById(respuestaDTO.topicoId()).get();

        // Crear instancia de Respuesta pasando los parámetros en el orden correcto
        var revisar = new Respuesta(
                null,                       // ID
                respuestaDTO.solucion(),    // Solución
                usuario,                    // Autor (Usuario)
                topico,                     // Tópico (Topico)
                respuestaDTO.fechaCreacion(),// Fecha de Creación
                respuestaDTO.activo()       // Activo
        );

        repository.save(revisar);
        return new RespuestaCreadaDTO(revisar);
    }
}
