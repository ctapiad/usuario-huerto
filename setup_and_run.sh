#!/bin/bash

# Script para configurar Oracle Wallet y ejecutar la aplicación
# Uso: ./setup_and_run.sh

echo "🔧 Configurando Oracle Wallet para el proyecto Usuario..."

# Variables
WALLET_ZIP="/Users/administrador/Documents/Base de datos/SQL Developer/Wallet_ctapiad.zip"
WALLET_EXTRACT_DIR="/Users/administrador/Documents/Base de datos/SQL Developer/wallet_extracted"
PROJECT_DIR="/Users/administrador/Documents/Fullstack2/usuario"

# Crear directorio de extracción si no existe
if [ ! -d "$WALLET_EXTRACT_DIR" ]; then
    echo "📁 Creando directorio para extraer wallet..."
    mkdir -p "$WALLET_EXTRACT_DIR"
fi

# Extraer wallet si no está extraído
if [ ! -f "$WALLET_EXTRACT_DIR/tnsnames.ora" ]; then
    echo "📦 Extrayendo wallet..."
    unzip -o "$WALLET_ZIP" -d "$WALLET_EXTRACT_DIR"
    echo "✅ Wallet extraído en: $WALLET_EXTRACT_DIR"
else
    echo "✅ Wallet ya está extraído"
fi

# Verificar archivos del wallet
echo "📋 Verificando archivos del wallet..."
ls -la "$WALLET_EXTRACT_DIR"

# Mostrar conexiones disponibles
echo "🔍 Conexiones disponibles en tnsnames.ora:"
if [ -f "$WALLET_EXTRACT_DIR/tnsnames.ora" ]; then
    grep -E "^[A-Z_]+ =" "$WALLET_EXTRACT_DIR/tnsnames.ora" | cut -d'=' -f1
fi

# Configurar variables de entorno
export TNS_ADMIN="$WALLET_EXTRACT_DIR"
export ORACLE_TNS_ADMIN="$WALLET_EXTRACT_DIR"

echo "🌍 Variables de entorno configuradas:"
echo "TNS_ADMIN=$TNS_ADMIN"
echo "ORACLE_TNS_ADMIN=$ORACLE_TNS_ADMIN"

# Cambiar al directorio del proyecto
cd "$PROJECT_DIR"

echo "🚀 Iniciando aplicación Spring Boot..."
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Doracle.net.tns_admin=$WALLET_EXTRACT_DIR -Djava.net.useSystemProxies=true"