package com.example.Foro_Hub.domain.respuesta;

import com.example.Foro_Hub.domain.topico.Topico;
import com.example.Foro_Hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "solucion", nullable = false)
    private String solucion;

    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "topico", referencedColumnName = "id", nullable = false)
    private Topico topico;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    public Respuesta() {
        // Constructor por defecto
    }
    public Respuesta(Long id, String solucion, Usuario autor, Topico topico, LocalDateTime fechaCreacion, boolean activo) {
        this.id = id;
        this.solucion = solucion;
        this.autor = autor;
        this.topico = topico;
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : LocalDateTime.now();
        this.activo = true; // La respuesta se activa por defecto
    }

    // Métodos getter
    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getSolucion() {
        return solucion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Topico getTopico() {
        return topico;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void actualizarRespuesta(RespuestaActualizadaDTO respuestaActualizadaDTO) {
        if (respuestaActualizadaDTO.solucion() != null) {
            this.solucion = respuestaActualizadaDTO.solucion();
        }
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void desactivarRespuesta() {
        this.activo = false;
    }
}
