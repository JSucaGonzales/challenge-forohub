package com.example.Foro_Hub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByActivoTrue(Pageable pageable);

    Optional<Usuario> findByEmail(String email);


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);
}
