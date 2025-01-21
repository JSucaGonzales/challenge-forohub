CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha DATETIME NOT NULL,
    status ENUM('ACTIV0', 'INACTIVO', 'SOLUCIONADO') NOT NULL,
    autor_id BIGINT,
    nombre_curso VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
