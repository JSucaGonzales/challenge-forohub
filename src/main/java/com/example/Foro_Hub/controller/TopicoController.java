package com.example.Foro_Hub.controller;

import com.example.Foro_Hub.domain.topico.*;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/topicos") // Ruta base para todas las operaciones relacionadas con tópicos.
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    // Repositorio para realizar operaciones CRUD relacionadas con tópicos.

    @Autowired
    private TopicoService topicoService;
    // Servicio que contiene la lógica de negocio para la gestión de tópicos.

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid TopicoDTO topicoDTO) throws ValidacionDeIntegridad {
        // Registra un nuevo tópico utilizando el servicio.
        var topicoRegistrado = topicoService.topicoCreado(topicoDTO);
        return ResponseEntity.ok(topicoRegistrado); // Devuelve el tópico registrado.
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ListarTopicosDTO>> listarTopicos(@PageableDefault(size = 10) Pageable paged) {
        // Lista todos los tópicos activos con paginación.
        return ResponseEntity.ok(
                topicoRepository.findByActivoTrue((org.springframework.data.domain.Pageable) paged)
                        .map(ListarTopicosDTO::new)
        );
    }

    @PutMapping("/actualizar/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoActualizadoDTO topicoActualizadoDTO) {
        // Obtiene el tópico por ID y actualiza sus datos.
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarTopico(topicoActualizadoDTO); // Actualiza los datos del tópico.
        return ResponseEntity.ok(new RespuestaTopicoDTO(
                topico.getId(),          // ID del tópico.
                topico.getTitulo(),      // Título del tópico.
                topico.getMensaje(),     // Mensaje o contenido del tópico.
                topico.getStatus(),      // Estado del tópico.
                topico.getAutor(),       // Autor del tópico.
                topico.getNombreCurso(), // Nombre del curso relacionado.
                topico.getFecha()        // Fecha de creación o actualización.
        ));
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        // Obtiene el tópico por ID y lo desactiva (eliminación lógica).
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico(); // Cambia su estado a inactivo.
        return ResponseEntity.noContent().build(); // Responde con un código HTTP 204 (sin contenido).
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> obtenerTopico(@PathVariable Long id) {
        // Obtiene un tópico específico por ID y lo devuelve como DTO.
        Topico topico = topicoRepository.getReferenceById(id);
        var topicoId = new RespuestaTopicoDTO(
                topico.getId(),          // ID del tópico.
                topico.getTitulo(),      // Título del tópico.
                topico.getMensaje(),     // Mensaje o contenido del tópico.
                topico.getStatus(),      // Estado del tópico.
                topico.getAutor(),       // Autor del tópico.
                topico.getNombreCurso(), // Nombre del curso relacionado.
                topico.getFecha()        // Fecha de creación o actualización.
        );
        return ResponseEntity.ok(topicoId); // Devuelve el DTO con los datos del tópico.
    }
}

