# 🚀 Acceso Rápido - Proyecto Usuario MongoDB

## 📱 URLs de Acceso

### Swagger UI (Documentación API Interactiva)
```
http://localhost:8081/swagger-ui/index.html
```
o
```
http://localhost:8081/swagger-ui.html
```

### API Base URL
```
http://localhost:8081/api
```

---

## 📋 Endpoints Disponibles

### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/usuarios` | Listar todos los usuarios |
| `GET` | `/api/usuarios/{id}` | Obtener usuario por ID (String ObjectId) |
| `GET` | `/api/usuarios/email/{email}` | Buscar usuario por email |
| `GET` | `/api/usuarios/buscar/{nombre}` | Buscar usuarios por nombre |
| `GET` | `/api/usuarios/tipo/{idTipo}` | Filtrar usuarios por tipo |
| `GET` | `/api/usuarios/{id}/dto` | Obtener DTO de usuario |
| `POST` | `/api/usuarios` | Crear nuevo usuario |
| `PUT` | `/api/usuarios` | Modificar usuario existente |
| `DELETE` | `/api/usuarios/{id}` | Eliminar usuario |

---

## 🧪 Ejemplos de Prueba con cURL

### Listar todos los usuarios
```bash
curl -X GET http://localhost:8081/api/usuarios | jq .
```

### Obtener usuario por ID
```bash
curl -X GET http://localhost:8081/api/usuarios/673374a5f1234567890abcde | jq .
```

### Buscar usuario por email
```bash
curl -X GET http://localhost:8081/api/usuarios/email/juan@huerto.cl | jq .
```

### Crear nuevo usuario
```bash
curl -X POST http://localhost:8081/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María González",
    "email": "maria@huerto.cl",
    "password": "password123",
    "direccion": "Av. Principal 456",
    "telefono": 987654321,
    "idComuna": 5,
    "idTipoUsuario": 2
  }' | jq .
```

### Actualizar usuario
```bash
curl -X PUT http://localhost:8081/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "id": "673374a5f1234567890abcde",
    "nombre": "María González Updated",
    "email": "maria@huerto.cl",
    "password": "newpassword456",
    "direccion": "Nueva Dirección 789",
    "telefono": 912345678,
    "idComuna": 3,
    "idTipoUsuario": 2
  }' | jq .
```

### Eliminar usuario
```bash
curl -X DELETE http://localhost:8081/api/usuarios/673374a5f1234567890abcde
```

---

## 🗄️ Base de Datos MongoDB

### Conexión
- **Cluster**: huerto.bi4rvwk.mongodb.net
- **Base de datos**: Huerto
- **Colección**: usuarios
- **Usuario**: ctapiad_db_user

### Conexión desde MongoDB Compass
```
mongodb+srv://ctapiad_db_user:MhRBXg6OTYK9AqQv@huerto.bi4rvwk.mongodb.net/Huerto
```

### Conexión desde mongosh
```bash
mongosh "mongodb+srv://ctapiad_db_user:MhRBXg6OTYK9AqQv@huerto.bi4rvwk.mongodb.net/Huerto"
```

---

## 🔧 Comandos Útiles

### Iniciar la aplicación
```bash
./mvnw spring-boot:run
```

### Ejecutar tests
```bash
./mvnw test
```

### Compilar proyecto
```bash
./mvnw clean package
```

### Limpiar y recompilar
```bash
./mvnw clean compile
```

---

## 📊 Estructura de Datos

### Modelo Usuario (MongoDB Document)
```json
{
  "_id": "ObjectId generado por MongoDB (String de 24 caracteres)",
  "nombre": "String - Nombre del usuario",
  "email": "String - Email único del usuario",
  "password": "String - Contraseña (debe ser hasheada)",
  "fechaRegistro": "Date - Fecha de registro",
  "direccion": "String - Dirección del usuario",
  "telefono": "Integer - Número de teléfono",
  "idComuna": "Integer - ID de la comuna",
  "idTipoUsuario": "Long - Tipo de usuario (1: Admin, 2: Cliente, etc.)"
}
```

### Ejemplo Real
```json
{
  "_id": "673374a5f1234567890abcde",
  "nombre": "Juan Pérez",
  "email": "juan@huerto.cl",
  "password": "$2a$10$hashed_password",
  "fechaRegistro": "2025-11-12T12:00:00.000Z",
  "direccion": "Av. Libertador 1234",
  "telefono": 987654321,
  "idComuna": 5,
  "idTipoUsuario": 2
}
```

---

## 🔑 Cambios Importantes desde Oracle

### IDs
- **Antes (Oracle)**: `Long` numérico (1, 2, 3...)
- **Ahora (MongoDB)**: `String` ObjectId ("673374a5f1234567890abcde")

### Campo ID
- **Antes**: `idUsuario`
- **Ahora**: `id`

### Endpoints actualizados
Todos los endpoints que recibían `/usuarios/{id}` ahora esperan un String ObjectId en lugar de Long.

---

## 📝 Notas Adicionales

- El servidor corre por defecto en el puerto **8081**
- Swagger UI proporciona interfaz interactiva para probar todos los endpoints
- La base de datos MongoDB está en la nube (MongoDB Atlas) en la región de São Paulo
- Los tests unitarios verifican toda la funcionalidad

---

## 🆘 Solución de Problemas

### Error de conexión a MongoDB
Verificar que:
1. La cadena de conexión en `application.properties` es correcta
2. Tu IP está en la lista blanca de MongoDB Atlas
3. Las credenciales son correctas

### Puerto 8081 ocupado
```bash
# Encontrar proceso usando el puerto
lsof -i :8081

# Matar el proceso
kill -9 <PID>
```

### Limpiar compilación
```bash
./mvnw clean
rm -rf target/
```
