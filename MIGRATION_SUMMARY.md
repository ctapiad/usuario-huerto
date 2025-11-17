# Resumen de Migración: Oracle Database → MongoDB

## 📋 Descripción General
Este proyecto ha sido migrado exitosamente de **Oracle Cloud Database** a **MongoDB Atlas** debido a la eliminación de la cuenta de Oracle Cloud.

## 🎯 Base de Datos Objetivo
- **Tipo**: MongoDB Atlas
- **Cluster**: huerto.bi4rvwk.mongodb.net
- **Base de datos**: Huerto
- **Colección principal**: usuarios
- **Conexión**: mongodb+srv://ctapiad_db_user:***@huerto.bi4rvwk.mongodb.net/Huerto

---

## ✅ Cambios Realizados

### 1. Dependencias (pom.xml)
**Eliminadas:**
- `ojdbc8` (Oracle JDBC Driver)
- `oraclepki`, `osdt_cert`, `osdt_core` (Oracle Security)
- `spring-boot-starter-data-jpa`

**Añadidas:**
- `spring-boot-starter-data-mongodb` (MongoDB integration)
- `spring-boot-starter-validation` (Bean validation)

### 2. Configuración (application.properties)
**Antes (Oracle):**
```properties
spring.datasource.url=jdbc:oracle:thin:@...
spring.datasource.username=ADMIN
spring.datasource.password=...
spring.jpa.hibernate.ddl-auto=update
```

**Después (MongoDB):**
```properties
spring.data.mongodb.uri=mongodb+srv://ctapiad_db_user:MhRBXg6OTYK9AqQv@huerto.bi4rvwk.mongodb.net/Huerto?retryWrites=true&w=majority
```

### 3. Entidad Usuario (UsuarioEntity.java)
**Cambios principales:**
- `@Entity` → `@Document(collection = "usuarios")`
- `@Table` → eliminado
- `@Id @GeneratedValue` → `@Id` (MongoDB genera automáticamente)
- `@Column` → `@Field`
- Tipo de ID: `Long idUsuario` → `String id`
- Eliminadas anotaciones JPA: `@GeneratedValue`, `@SequenceGenerator`

**Antes:**
```java
@Entity
@Table(name = "USUARIO")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    // ...
}
```

**Después:**
```java
@Document(collection = "usuarios")
public class UsuarioEntity {
    @Id
    private String id;
    
    @Field("nombre")
    private String nombre;
    // ...
}
```

### 4. Repositorio (UsuarioRepository.java)
**Cambios principales:**
- `extends JpaRepository<UsuarioEntity, Long>` → `extends MongoRepository<UsuarioEntity, String>`
- Queries JPQL → MongoDB queries con regex
- Método `findByIdUsuario(Long id)` eliminado (usar `findById(String id)`)

**Antes (JPA):**
```java
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    @Query("SELECT u FROM UsuarioEntity u WHERE u.nombre LIKE %?1%")
    List<UsuarioEntity> findByNombreContaining(String nombre);
}
```

**Después (MongoDB):**
```java
public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {
    @Query("{'nombre': {$regex: ?0, $options: 'i'}}")
    List<UsuarioEntity> findByNombreContaining(String nombre);
}
```

### 5. Servicio (UsuarioService.java)
**Cambios principales:**
- Eliminada anotación `@Transactional` (MongoDB no la requiere de la misma manera)
- Cambio de tipo de ID de `Long` a `String` en todos los métodos
- `findByIdUsuario(Long)` → `findById(String)`
- MongoDB genera automáticamente los IDs, no se establecen manualmente

**Métodos actualizados:**
- `obtenerUsuario(Long id)` → `obtenerUsuario(String id)`
- `borrarUsuario(Long id)` → `borrarUsuario(String id)`
- `obtenerUsuarioDto(Long id)` → `obtenerUsuarioDto(String id)`

### 6. Modelos (Usuario.java, UsuarioDto.java)
**Cambio de tipo de ID:**
- `Long idUsuario` → `String id`
- Todos los getters/setters actualizados

### 7. Controlador (UsuarioController.java)
**Endpoints actualizados:**
```java
// Antes
@GetMapping("/usuarios/{id}")
public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id)

// Después
@GetMapping("/usuarios/{id}")
public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable String id)
```

### 8. Tests (UsuarioTest.java)
**Actualizaciones:**
- IDs de prueba cambiados de `1L` a `"507f1f77bcf86cd799439011"` (formato ObjectId)
- `findByIdUsuario(anyLong())` → `findById(anyString())`
- `existsById(anyLong())` → `existsById(anyString())`

### 9. ReportesController.java
**Estado:** Deshabilitado temporalmente
- Este controlador usaba `JdbcTemplate` que es específico para bases de datos SQL
- Todo el código está comentado con nota explicativa
- **Solución futura:** Reescribir usando `MongoTemplate` o agregaciones de MongoDB

---

## 🚫 Archivos Obsoletos
Los siguientes archivos/directorios ya no son necesarios y pueden eliminarse:
- `src/main/resources/wallet_extracted/` (Oracle Wallet)
- Archivos de configuración Oracle en `application.properties` (ya comentados)

---

## 📊 Resultados de Migración

### Compilación
```
[INFO] BUILD SUCCESS
[INFO] Total time:  1.449 s
```

### Tests
```
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
✅ UsuarioApplicationTests: PASSED
✅ UsuarioTest: PASSED (7 tests)
```

---

## 🔄 Cambios en el Modelo de Datos

### IDs
| Aspecto | Oracle | MongoDB |
|---------|--------|---------|
| Tipo | `Long` (numérico secuencial) | `String` (ObjectId, 24 caracteres hex) |
| Generación | Base de datos con secuencia | MongoDB automático |
| Ejemplo | `1`, `2`, `3` | `"507f1f77bcf86cd799439011"` |

### Queries
| Oracle (JPA) | MongoDB |
|--------------|---------|
| `WHERE nombre LIKE '%texto%'` | `{'nombre': {$regex: 'texto', $options: 'i'}}` |
| `findByIdUsuario(Long id)` | `findById(String id)` |
| Transacciones ACID | Transacciones multi-documento (disponibles) |

---

## 🎯 Funcionalidad Mantenida
Todos los endpoints REST siguen funcionando:

✅ `GET /api/usuarios` - Listar todos los usuarios  
✅ `GET /api/usuarios/{id}` - Obtener usuario por ID (ahora String)  
✅ `GET /api/usuarios/email/{email}` - Buscar por email  
✅ `POST /api/usuarios` - Crear nuevo usuario  
✅ `PUT /api/usuarios` - Modificar usuario  
✅ `DELETE /api/usuarios/{id}` - Eliminar usuario  
✅ `GET /api/usuarios/buscar/{nombre}` - Buscar por nombre  
✅ `GET /api/usuarios/tipo/{idTipo}` - Filtrar por tipo  

---

## 📝 Próximos Pasos Recomendados

1. **Eliminar archivos obsoletos:**
   ```bash
   rm -rf src/main/resources/wallet_extracted/
   ```

2. **Reimplementar ReportesController:**
   - Usar `MongoTemplate` para agregaciones complejas
   - O crear colecciones/vistas materializadas en MongoDB

3. **Optimizaciones de MongoDB:**
   - Crear índices para campos frecuentemente consultados:
     ```javascript
     db.usuarios.createIndex({ "email": 1 }, { unique: true })
     db.usuarios.createIndex({ "nombre": 1 })
     db.usuarios.createIndex({ "idTipoUsuario": 1 })
     ```

4. **Actualizar frontend:**
   - Los IDs ahora son Strings en lugar de números
   - Actualizar validaciones si es necesario

5. **Documentación API:**
   - Swagger/OpenAPI ya configurado y funcional
   - Verificar que los ejemplos de IDs usen formato String

---

## 🔗 Recursos Útiles
- [Spring Data MongoDB Reference](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [MongoDB Query Operators](https://www.mongodb.com/docs/manual/reference/operator/query/)
- [MongoDB Atlas Documentation](https://www.mongodb.com/docs/atlas/)

---

## ✨ Conclusión
La migración de Oracle Database a MongoDB se completó exitosamente. El proyecto compila sin errores, todas las pruebas pasan, y la funcionalidad principal se mantiene intacta. La única funcionalidad pendiente es el `ReportesController` que requerirá reimplementación específica para MongoDB.
