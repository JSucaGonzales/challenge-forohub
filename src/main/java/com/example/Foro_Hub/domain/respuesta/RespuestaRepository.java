package com.example.Foro_Hub.domain.respuesta;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Page<Respuesta> findByActivoTrue(Pageable pageable); // Asegúrate de que 'Pageable' sea de Spring Data
}