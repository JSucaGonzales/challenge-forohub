CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion DATETIME NOT NULL,
    solucion TEXT NOT NULL,
    autor BIGINT,
    topico BIGINT,
    activo BOOLEAN NOT NULL,
    FOREIGN KEY (autor) REFERENCES usuarios(id),
    FOREIGN KEY (topico) REFERENCES topicos(id)
);
