# Sistema de Gestión de Usuarios - Productos del Campo

Este proyecto es un microservicio de gestión de usuarios adaptado para trabajar con un sistema de productos del campo, utilizando una base de datos Oracle.

## Estructura de la Base de Datos

La aplicación utiliza la siguiente tabla de usuarios en Oracle:

```sql
CREATE TABLE usuario (
    id_usuario      NUMBER NOT NULL,
    nombre          VARCHAR2(100) NOT NULL,
    email           VARCHAR2(100) NOT NULL,
    password        VARCHAR2(100) NOT NULL,
    fecha_registro  DATE NOT NULL,
    direccion       VARCHAR2(200),
    telefono        NUMBER(9),
    id_comuna       NUMBER(3) NOT NULL,
    id_tipo_usuario NUMBER NOT NULL
);
ALTER TABLE usuario ADD CONSTRAINT usuario_pk PRIMARY KEY ( id_usuario );
```

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Data JPA**
- **Oracle Database**
- **Maven**
- **Swagger/OpenAPI 3**
- **Lombok**

## Configuración

### Variables de Entorno

Configura las siguientes variables en el archivo `.env`:

```properties
ORACLE_DB_HOST=localhost
ORACLE_DB_PORT=1521
ORACLE_DB_SID=XE
ORACLE_DB_USERNAME=tu_usuario
ORACLE_DB_PASSWORD=tu_password
SERVER_PORT=8080
```

### Base de Datos Oracle

1. Asegúrate de tener Oracle Database instalado y ejecutándose
2. Crea la tabla de usuarios usando el script SQL proporcionado
3. Configura las credenciales de conexión en el archivo `.env`

## Instalación y Ejecución

1. Clona el repositorio
2. Configura las variables de entorno en `.env`
3. Ejecuta la aplicación:

```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Usuarios

- `GET /api/usuarios` - Obtener todos los usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `GET /api/usuarios/email/{email}` - Obtener usuario por email
- `POST /api/usuarios` - Crear nuevo usuario
- `PUT /api/usuarios` - Modificar usuario existente
- `DELETE /api/usuarios/{id}` - Eliminar usuario
- `GET /api/usuarios/{id}/dto` - Obtener DTO de usuario
- `GET /api/usuarios/buscar/{nombre}` - Buscar usuarios por nombre
- `GET /api/usuarios/tipo/{idTipoUsuario}` - Obtener usuarios por tipo

## Documentación API

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación Swagger en:

```
http://localhost:8080/swagger-ui/index.html
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/fullstack/usuario/
│   │   ├── controller/          # Controladores REST
│   │   ├── service/             # Lógica de negocio
│   │   ├── repository/          # Acceso a datos
│   │   ├── model/               # Modelos de dominio
│   │   │   ├── entity/          # Entidades JPA
│   │   │   └── dto/             # Objetos de transferencia de datos
│   │   ├── config/              # Configuraciones
│   │   └── UsuarioApplication.java
│   └── resources/
│       └── application.properties
└── test/                        # Pruebas unitarias
```

## Notas de Migración

Este proyecto ha sido migrado desde MySQL a Oracle Database con los siguientes cambios principales:

- Reemplazo del driver MySQL por Oracle JDBC
- Actualización de entidades JPA para mapear correctamente con la estructura Oracle
- Adaptación de tipos de datos (VARCHAR2, NUMBER, DATE)
- Configuración específica para Oracle Dialect
- Nuevos endpoints adaptados a la estructura de ID numérico en lugar de RUT

## Contribución

1. Fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request
