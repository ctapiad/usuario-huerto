#!/bin/bash

# Script simple para ejecutar la aplicación sin tests
# Uso: ./run_app.sh

echo "🚀 Iniciando aplicación Usuario - Sistema de Productos del Campo..."

# Variables (usando rutas sin espacios)
WALLET_EXTRACT_DIR="/Users/administrador/Documents/Base de datos/SQL Developer/wallet_extracted"

# Configurar variables de entorno para Oracle
export TNS_ADMIN="$WALLET_EXTRACT_DIR"
export ORACLE_TNS_ADMIN="$WALLET_EXTRACT_DIR"

echo "🌍 Configurado TNS_ADMIN: $TNS_ADMIN"

# Compilar sin ejecutar tests
echo "🔨 Compilando proyecto..."
./mvnw clean compile -DskipTests

# Ejecutar aplicación en puerto 8081
echo "🚀 Ejecutando aplicación en puerto 8081..."
export JAVA_OPTS="-Doracle.net.tns_admin=$WALLET_EXTRACT_DIR"
./mvnw spring-boot:run -DskipTests