#!/bin/bash
# Script para configurar la instancia EC2 de AWS

echo "=== Configurando instancia EC2 para Spring Boot ==="

# Actualizar sistema
echo "1. Actualizando sistema..."
sudo apt update && sudo apt upgrade -y

# Instalar Java 17
echo "2. Instalando Java 17..."
sudo apt install -y openjdk-17-jdk

# Verificar instalación de Java
echo "3. Verificando Java..."
java -version

# Crear directorio para la aplicación
echo "4. Creando directorio para la aplicación..."
mkdir -p /home/ubuntu/logs

# Configurar firewall para permitir puerto 8081
echo "5. Configurando firewall UFW..."
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 8081/tcp  # Spring Boot
sudo ufw --force enable

# Crear servicio systemd para auto-inicio
echo "6. Creando servicio systemd..."
sudo tee /etc/systemd/system/usuario-service.service > /dev/null << 'EOF'
[Unit]
Description=Usuario Service - Spring Boot Application
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu
ExecStart=/usr/bin/java -jar /home/ubuntu/usuario-service.jar --server.port=8081
Restart=always
RestartSec=10
StandardOutput=append:/home/ubuntu/logs/app.log
StandardError=append:/home/ubuntu/logs/app-error.log

[Install]
WantedBy=multi-user.target
EOF

# Recargar systemd
sudo systemctl daemon-reload

echo "=== Configuración completada ==="
echo ""
echo "IMPORTANTE: Ahora debes configurar el Security Group en AWS:"
echo "1. Ve a EC2 Console → Security Groups"
echo "2. Selecciona el security group de tu instancia"
echo "3. Agrega estas reglas de entrada (Inbound Rules):"
echo "   - Type: Custom TCP, Port: 8081, Source: 0.0.0.0/0 (para acceso desde cualquier IP)"
echo "   - Type: SSH, Port: 22, Source: Tu IP (para seguridad)"
echo ""
echo "Después del primer deployment, ejecuta:"
echo "  sudo systemctl enable usuario-service"
echo "  sudo systemctl start usuario-service"
