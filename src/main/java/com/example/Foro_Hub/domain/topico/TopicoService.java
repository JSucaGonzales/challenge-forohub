package com.example.Foro_Hub.domain.topico;

import com.example.Foro_Hub.domain.usuario.UsuarioRepository;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public RespuestaTopicoDTO topicoCreado(TopicoDTO topicoDTO) throws ValidacionDeIntegridad {
        if (!usuarioRepository.findById(topicoDTO.autorId()).isPresent()) {
            throw new ValidacionDeIntegridad("Este ID de usuario no está registrado en la base de datos.");
        }
        var title = topicoDTO.titulo();
        if (title != null && topicoRepository.existsByTituloIgnoreCase(topicoDTO.titulo())) {
            throw new ValidacionDeIntegridad("Este título ya está presente en la base de datos. Por favor revise el tópico existente.");
        }
        String mensaje = topicoDTO.mensaje();
        if (mensaje != null && topicoRepository.existsByMensajeIgnoreCase(topicoDTO.mensaje())) {
            throw new ValidacionDeIntegridad("Este mensaje ya está presente en la base de datos. Por favor revise el tópico existente.");
        }
        var usuario = usuarioRepository.findById(topicoDTO.autorId()).get();
        var topicoI = new Topico(topicoDTO.titulo(), topicoDTO.mensaje(), topicoDTO.fecha(), topicoDTO.status(), usuario, topicoDTO.nombreCurso());
        topicoRepository.save(topicoI);
        return new RespuestaTopicoDTO(topicoI);
    }
}
