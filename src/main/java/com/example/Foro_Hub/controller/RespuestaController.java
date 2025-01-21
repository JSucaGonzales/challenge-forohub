package com.example.Foro_Hub.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Foro_Hub.domain.respuesta.*;
import com.example.Foro_Hub.infra.errores.ValidacionDeIntegridad;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController {

    private RespuestaRepository respuestaRepository;
    private RespuestaService respuestaService;

    // Inyección de dependencias por constructor
    public RespuestaController(RespuestaRepository respuestaRepository, RespuestaService respuestaService) {
        this.respuestaRepository = respuestaRepository;
        this.respuestaService = respuestaService;
    }

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarRespuesta(@RequestBody @Valid RespuestaDTO respuestaDTO) throws ValidacionDeIntegridad {
        return ResponseEntity.ok(respuestaService.respuestaCreadaDTO(respuestaDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<ListarRespuestasDTO>> listarRespuestas(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(respuestaRepository.findByActivoTrue(paged).map(ListarRespuestasDTO::new));
    }

    @PutMapping("/actualizar/{id}")
    @Transactional
    public ResponseEntity<?> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestaActualizadaDTO respuestaActualizadaDTO) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(respuestaActualizadaDTO);
        return ResponseEntity.ok(new RespuestaDTO(
                respuesta.getSolucion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getActivo(),
                respuesta.getFechaCreacion()
        ));
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.desactivarRespuesta();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        return ResponseEntity.ok(new RespuestaDTO(
                respuesta.getSolucion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getActivo(),
                respuesta.getFechaCreacion()
        ));
    }
}
