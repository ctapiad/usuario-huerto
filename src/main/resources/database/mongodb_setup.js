// MongoDB Shell Script
// =====================
// Este archivo debe ejecutarse con MongoDB Shell (mongosh), NO con node.js
// Uso: mongosh "mongodb+srv://..." --file mongodb_setup.js
//
// Base de datos: Huerto
// Colección: usuario

// Conectar a la base de datos Huerto
use Huerto;

// Crear la colección usuario (si no existe)
db.createCollection("usuario");

// Crear índices para optimizar las consultas
db.usuario.createIndex({ "email": 1 }, { unique: true, name: "idx_email_unique" });
db.usuario.createIndex({ "nombre": 1 }, { name: "idx_nombre" });
db.usuario.createIndex({ "id_tipo_usuario": 1 }, { name: "idx_tipo_usuario" });
db.usuario.createIndex({ "id_comuna": 1 }, { name: "idx_comuna" });
db.usuario.createIndex({ "fecha_registro": -1 }, { name: "idx_fecha_registro" });

// Verificar índices creados
db.usuario.getIndexes();

// Crear un usuario de ejemplo (opcional - comentado)
/*
db.usuario.insertOne({
    nombre: "Usuario Demo",
    email: "demo@huerto.cl",
    password: "$2a$10$demo_hash_password",
    fecha_registro: new Date(),
    direccion: "Calle Demo 123",
    telefono: 123456789,
    id_comuna: 1,
    id_tipo_usuario: 2
});
*/

print("✅ Base de datos 'Huerto' configurada correctamente");
print("✅ Colección 'usuario' creada");
print("✅ Índices creados para optimizar consultas");
