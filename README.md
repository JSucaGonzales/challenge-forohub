Foro Hub API
Foro Hub es una API REST creada con Java y Spring Boot para gestionar un sistema de foros. Este proyecto incluye funcionalidades básicas de CRUD para manejar tópicos, respuestas, usuarios, y más, aplicando buenas prácticas de diseño y seguridad.

🚀 Funcionalidades
Gestión de Tópicos:
Crear, listar, actualizar y eliminar tópicos.
Gestión de Respuestas:
Registrar, listar, actualizar y eliminar respuestas a los tópicos.
Autenticación y Seguridad:
Implementación de autenticación JWT.
Restricción de acceso a rutas mediante roles.
Validaciones:
Validación de datos con reglas de negocio y anotaciones de validación.
🛠️ Tecnologías Utilizadas
Java 17
Spring Boot 3.4.1
Spring Security (JWT)
Hibernate (JPA)
MySQL (Base de datos)
Lombok
Swagger (Documentación de la API)
📂 Estructura del Proyecto
bash
Copiar
Editar
src/main/java/com/example/Foro_Hub
├── controller         # Controladores REST
├── domain             # Modelos de datos y lógica de negocio
├── infra              # Infraestructura y configuraciones
│   ├── errores        # Manejo de errores personalizados
│   ├── security       # Configuración de autenticación JWT
└── resources
    ├── application.properties # Configuración de la base de datos
    └── data.sql               # Datos iniciales
🔧 Configuración Inicial
Clonar el repositorio:

bash
Copiar
Editar
git clone <URL del repositorio>
Configurar la base de datos en el archivo application.properties:

properties
Copiar
Editar
spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
Ejecutar la aplicación:

bash
Copiar
Editar
./mvnw spring-boot:run
Acceder a la documentación Swagger:

URL: http://localhost:8080/swagger-ui/index.html
