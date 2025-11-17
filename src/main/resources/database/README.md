# Database Scripts

## MongoDB Setup

### Script de Inicialización
- **Archivo**: `mongodb_setup.js`
- **Descripción**: Script para configurar la base de datos MongoDB "Huerto" con índices optimizados

### Cómo ejecutar el script

#### Opción 1: MongoDB Shell (mongosh)
```bash
mongosh "mongodb+srv://ctapiad_db_user:PASSWORD@huerto.bi4rvwk.mongodb.net/Huerto" mongodb_setup.js
```

#### Opción 2: MongoDB Compass
1. Conectar a tu cluster de MongoDB Atlas
2. Abrir la base de datos "Huerto"
3. Ir a "Aggregations" o abrir una consola
4. Copiar y ejecutar el contenido de `mongodb_setup.js`

#### Opción 3: Crear índices manualmente en Atlas
1. Acceder a MongoDB Atlas
2. Navegar a Database > Browse Collections
3. Seleccionar base de datos "Huerto" > colección "usuarios"
4. Click en "Indexes" > "Create Index"

### Índices Creados

Los siguientes índices se crean automáticamente:

| Campo | Tipo | Unique | Nombre | Propósito |
|-------|------|--------|--------|-----------|
| `email` | Ascendente | Sí | `idx_email_unique` | Búsqueda rápida por email y garantizar unicidad |
| `nombre` | Ascendente | No | `idx_nombre` | Búsquedas por nombre |
| `idTipoUsuario` | Ascendente | No | `idx_tipo_usuario` | Filtrado por tipo de usuario |
| `idComuna` | Ascendente | No | `idx_comuna` | Filtrado por comuna |
| `fechaRegistro` | Descendente | No | `idx_fecha_registro` | Ordenamiento por fecha de registro |

---

## Archivos Legacy

### legacy_oracle_setup.sql.bak
Script de configuración de Oracle Database (OBSOLETO)
- Este archivo se mantiene solo como referencia histórica
- El proyecto ahora usa MongoDB
- No ejecutar este script

---

## Estructura de la Base de Datos MongoDB

### Base de Datos: `Huerto`

### Colección: `usuarios`

#### Esquema del Documento
```json
{
  "_id": "ObjectId (generado automáticamente por MongoDB)",
  "nombre": "String",
  "email": "String (único)",
  "password": "String (hash)",
  "fechaRegistro": "Date",
  "direccion": "String",
  "telefono": "Integer",
  "idComuna": "Integer",
  "idTipoUsuario": "Long"
}
```

#### Ejemplo de Documento
```json
{
  "_id": "673374a5f1234567890abcde",
  "nombre": "Juan Pérez",
  "email": "juan.perez@huerto.cl",
  "password": "$2a$10$hashed_password_here",
  "fechaRegistro": "2025-11-12T12:00:00.000Z",
  "direccion": "Av. Libertador 1234, Santiago",
  "telefono": 987654321,
  "idComuna": 5,
  "idTipoUsuario": 2
}
```

---

## Conexión a MongoDB

La conexión se configura en `application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://ctapiad_db_user:PASSWORD@huerto.bi4rvwk.mongodb.net/Huerto?retryWrites=true&w=majority
```

### Parámetros de Conexión
- **Host**: huerto.bi4rvwk.mongodb.net (MongoDB Atlas)
- **Database**: Huerto
- **Usuario**: ctapiad_db_user
- **Replica Set**: atlas-11mbdy-shard-0
- **Región**: SA_EAST_1 (São Paulo, AWS)
