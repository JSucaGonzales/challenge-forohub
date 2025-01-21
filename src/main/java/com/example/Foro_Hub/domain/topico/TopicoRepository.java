package com.example.Foro_Hub.domain.topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByMensajeIgnoreCase(String mensaje);
    Page<Topico> findByActivoTrue(Pageable paginacion);
    @Query("""
            select t from Topico t
            where
            t.activo = true
            and t.status = :status
            and t.nombreCurso = :nombreCurso
            order by rand()
            limit 1
            """)
    Topico elegirTopicoAleatorioPorStatusYCurso(@Param("status") Status status, @Param("nombreCurso") String nombreCurso);
    @Query("""
            select t.activo
            from Topico t
            where
            t.id = :idTopico
            """)
    boolean findActivoById(@Param("idTopico") Long idTopico);
}