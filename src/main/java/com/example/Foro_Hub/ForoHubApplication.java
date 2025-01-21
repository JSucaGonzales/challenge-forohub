package com.example.Foro_Hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.Foro_Hub.domain")
// Habilita los repositorios JPA en el paquete especificado.
// Esto permite a Spring Boot detectar automáticamente las interfaces `@Repository` en este paquete.

// @EnableJpaRepositories(basePackages = "com.example.Foro_Hub.domain.respuesta")
// Línea comentada que permitiría habilitar repositorios específicos para el subpaquete "respuesta".

// @EntityScan(basePackages = "com.example.Foro_Hub.domain.usuario")
// Línea comentada para escanear entidades JPA en el subpaquete "usuario". Útil si las entidades no se detectan automáticamente.
public class ForoHubApplication {
	public static void main(String[] args) {
		// Punto de entrada de la aplicación Spring Boot.
		// Este método inicia el contexto de Spring y arranca la aplicación.
		SpringApplication.run(ForoHubApplication.class, args);
	}
}
