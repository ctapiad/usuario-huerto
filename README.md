# Sistema de Gestión de Usuarios - HuertoHogar 🌱

API REST para la gestión de usuarios del sistema HuertoHogar, desplegada en AWS EC2 con MongoDB Atlas.

## 🚀 API en Producción

- **URL Base**: http://34.193.190.24:8081/api/usuarios
- **Swagger UI**: http://34.193.190.24:8081/swagger-ui.html
- **IP Elástica**: 34.193.190.24 (permanente)
- **Estado**: ✅ Activo con CI/CD automatizado

## 🛠️ Stack Tecnológico

- **Backend**: Java 17 + Spring Boot 3.4.5
- **Base de Datos**: MongoDB Atlas (Cluster en AWS SA-EAST-1)
- **Cloud**: AWS EC2 (Ubuntu 24.04, IP Elástica)
- **CI/CD**: GitHub Actions
- **Documentación**: Swagger/OpenAPI 3
- **Build Tool**: Maven

## 📊 Estructura de la Base de Datos

**Base de Datos**: `Huerto`  
**Colección**: `usuario`

```javascript
{
  _id: ObjectId,           // ID único generado por MongoDB
  nombre: String,          // Nombre completo del usuario
  email: String,           // Email único (validado)
  password: String,        // Contraseña
  fecha_registro: Date,    // Fecha de registro
  direccion: String,       // Dirección
  telefono: Number,        // Teléfono
  id_comuna: Number,       // ID de la comuna
  id_tipo_usuario: Number  // Tipo: 1=Admin, 2=Vendedor, 3=Cliente
}
```

## 🔌 API Endpoints

### Gestión de Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/usuarios` | Listar todos los usuarios |
| `GET` | `/api/usuarios/{id}` | Obtener usuario por ID |
| `GET` | `/api/usuarios/email/{email}` | Buscar usuario por email |
| `POST` | `/api/usuarios` | Crear nuevo usuario |
| `PUT` | `/api/usuarios` | Actualizar usuario (campos parciales) |
| `DELETE` | `/api/usuarios/{id}` | Eliminar usuario |
| `GET` | `/api/usuarios/buscar/{nombre}` | Buscar por nombre (regex) |
| `GET` | `/api/usuarios/tipo/{idTipoUsuario}` | Filtrar por tipo de usuario |

### Ejemplo de Uso

**Crear Usuario:**
```bash
curl -X POST http://34.193.190.24:8081/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "password": "Pass123!",
    "direccion": "Calle Falsa 123",
    "telefono": 912345678,
    "idComuna": 15,
    "idTipoUsuario": 3
  }'
```

**Actualizar Email (con validación de duplicados):**
```bash
curl -X PUT http://34.193.190.24:8081/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "id": "691484cf402bef2b16612c8d",
    "email": "nuevo@email.com"
  }'
```

## 🚀 Instalación y Ejecución Local

### Prerrequisitos
- Java 17+
- Maven 3.8+
- Acceso a MongoDB Atlas (o instancia local)

### Pasos

1. **Clonar el repositorio:**
```bash
git clone https://github.com/ctapiad/usuario-huerto.git
cd usuario-huerto
```

2. **Configurar variables de entorno:**
Crear archivo `.env` basado en `.env.example`:
```bash
cp .env.example .env
# Editar .env con tus credenciales reales
```

O exportar la variable:
```bash
export MONGODB_URI="mongodb+srv://usuario:password@cluster.mongodb.net/Huerto"
```

3. **Compilar y ejecutar:**
```bash
./mvnw clean package
./mvnw spring-boot:run
```

4. **Acceder a Swagger:**
```
http://localhost:8081/swagger-ui.html
```

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/fullstack/usuario/
├── UsuarioApplication.java          # Punto de entrada
├── controller/
│   └── UsuarioController.java       # Endpoints REST
├── service/
│   └── UsuarioService.java          # Lógica de negocio
├── repository/
│   └── UsuarioRepository.java       # Acceso a MongoDB
├── model/
│   ├── Usuario.java                 # DTO de transferencia
│   ├── entity/
│   │   └── UsuarioEntity.java       # Entidad MongoDB (@Document)
│   └── dto/
│       └── UsuarioDto.java          # DTO sin password
└── config/
    └── SwaggerConfig.java           # Configuración OpenAPI
```

## ☁️ Infraestructura AWS

### EC2 Instance
- **Tipo**: t3.micro
- **OS**: Ubuntu 24.04 LTS
- **IP Elástica**: 34.193.190.24
- **Java**: OpenJDK 17
- **Servicio**: systemd (`usuario-service`)

### Security Group
| Puerto | Protocolo | Origen | Descripción |
|--------|-----------|--------|-------------|
| 22 | TCP | 0.0.0.0/0 | SSH |
| 8081 | TCP | 0.0.0.0/0 | API REST |

### MongoDB Atlas
- **Cluster**: huerto.bi4rvwk.mongodb.net
- **Región**: AWS SA-EAST-1 (São Paulo)
- **Tier**: M0 (Free)
- **Replica Set**: 3 nodos

## 🔄 CI/CD Pipeline

El proyecto usa **GitHub Actions** para despliegue automático:

1. **Push a `main`** → Trigger workflow
2. **Build**: `./mvnw clean package`
3. **Deploy**: SCP del JAR a EC2
4. **Restart**: `systemctl restart usuario-service`

**Workflow**: `.github/workflows/deploy.yml`

### GitHub Secrets Requeridos
- `AWS_HOST`: 34.193.190.24
- `AWS_USER`: ubuntu
- `SSH_PRIVATE_KEY`: Clave privada para SSH
- `MONGODB_URI`: Conexión a MongoDB Atlas

## ✨ Características Destacadas

- ✅ **Validación de Email Único**: Previene duplicados al crear/actualizar
- ✅ **Actualización Parcial**: PUT solo modifica campos no-null
- ✅ **ObjectId Nativo**: Uso correcto de IDs de MongoDB
- ✅ **Auto-reinicio**: Systemd reinicia el servicio si falla
- ✅ **Logs Centralizados**: `/home/ubuntu/app.log`
- ✅ **CORS Habilitado**: API accesible desde cualquier origen

## 🔐 Seguridad

### Variables de Entorno
**IMPORTANTE**: Este proyecto usa variables de entorno para credenciales sensibles.

- **Local**: Crear archivo `.env` desde `.env.example`
- **EC2**: Variables exportadas en el servicio systemd
- **GitHub Actions**: Configuradas en Secrets del repositorio

**NUNCA** commitear archivos con credenciales reales (`.env`, `application-prod.properties`, etc.)

## 🔧 Comandos Útiles

### En EC2 (SSH)
```bash
# Ver logs en tiempo real
sudo journalctl -u usuario-service -f

# Reiniciar servicio
sudo systemctl restart usuario-service

# Ver estado
sudo systemctl status usuario-service
```

### Localmente
```bash
# Compilar sin tests
./mvnw clean package -DskipTests

# Ver dependencias
./mvnw dependency:tree

# Formatear código
./mvnw spring-javaformat:apply
```

## 🐛 Troubleshooting

**Problema**: API no responde desde fuera  
**Solución**: Verificar Security Group tiene puerto 8081 abierto

**Problema**: Error SSL con MongoDB  
**Solución**: IP de EC2 debe estar en MongoDB Atlas Network Access (0.0.0.0/0)

**Problema**: Servicio no inicia  
**Solución**: `sudo systemctl status usuario-service` y revisar logs

## 📝 Historial de Migración

Este proyecto fue migrado de:
- ~~Oracle Cloud Database~~ → **MongoDB Atlas**
- ~~IDs numéricos~~ → **ObjectId de MongoDB**
- ~~IP temporal~~ → **IP Elástica permanente**

## 👥 Contribución

1. Fork del proyecto
2. Crea rama: `git checkout -b feature/nueva-funcionalidad`
3. Commit: `git commit -m 'Agrega funcionalidad X'`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Abre Pull Request

## 📄 Licencia

Este proyecto es parte del sistema HuertoHogar.

---

**Desarrollado por**: Equipo HuertoHogar  
**Última actualización**: Noviembre 2025
