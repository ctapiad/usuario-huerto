package com.fullstack.usuario.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuración de MongoDB para el proyecto.
 * 
 * La conexión se configura mediante application.properties:
 * spring.data.mongodb.uri=mongodb+srv://ctapiad_db_user:MhRBXg6OTYK9AqQv@huerto.bi4rvwk.mongodb.net/Huerto
 * 
 * Base de datos: Huerto
 * Colección principal: usuarios
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.fullstack.usuario.repository")
public class MongoDBConfig {
    // Spring Boot autoconfigura MongoDB usando application.properties
    // No necesitamos extender AbstractMongoClientConfiguration
}
