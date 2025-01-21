package com.example.Foro_Hub.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; @Column(name = "nombre", nullable = false)
    private String nombre; @Column(name = "username", nullable = false)
    private String username; @Column(name = "email", nullable = false)
    private String email; @Column(name = "password", nullable = false)
    private String password; @Column(name = "activo", nullable = false)
    private boolean activo;
    // Constructor adicional requerido por JPA
    public Usuario() {}
    // Constructor con argumentos
    public Usuario(String nombre, String username, String email, String password, boolean activo)
    { this.nombre = nombre; this.username = username; this.email = email; this.password = password; this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Método para desactivar el usuario
    public void deactivateUser() {
        this.activo = false;
    }


    public void actualizacionUsuario(ActualizacionUsuarioDTO actualizacionUsuarioDTO, BCryptPasswordEncoder passwordEncoder) {
        if (actualizacionUsuarioDTO.nombre() != null) {
            this.nombre = actualizacionUsuarioDTO.nombre();
        }
        if (actualizacionUsuarioDTO.email() != null) {
            this.email = actualizacionUsuarioDTO.email();
        }
        if (actualizacionUsuarioDTO.username() != null) {
            this.username = actualizacionUsuarioDTO.username();
        }
        if (actualizacionUsuarioDTO.clave() != null && !actualizacionUsuarioDTO.clave().isEmpty()) {
            this.password = passwordEncoder.encode(actualizacionUsuarioDTO.clave());
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}