CREATE TABLE usuarios (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   username VARCHAR(255) NOT NULL UNIQUE,
   password  VARCHAR(512) NOT NULL,
    activo BOOLEAN NOT NULL
);
