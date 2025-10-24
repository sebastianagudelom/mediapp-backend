#!/bin/bash

# Script de deployment para MediApp Backend en VPS
# Autor: MediApp Team
# Fecha: 23 de octubre de 2025

set -e  # Salir si hay algún error

echo "======================================"
echo "🚀 Iniciando deployment de MediApp Backend"
echo "======================================"

# Variables de configuración
VPS_USER="root"
VPS_HOST="56.125.172.86"
VPS_PATH="/root/mediapp-backend"
JAR_NAME="citas-backend-0.0.1-SNAPSHOT.jar"
SERVICE_NAME="mediapp-backend"

echo ""
echo "📦 Paso 1: Compilando el proyecto con Maven..."
./mvnw clean package -DskipTests

if [ ! -f "target/${JAR_NAME}" ]; then
    echo "❌ Error: No se encontró el archivo JAR compilado"
    exit 1
fi

echo "✅ Compilación exitosa!"
echo ""
echo "📤 Paso 2: Subiendo JAR a VPS..."

# Crear directorio en VPS si no existe
ssh ${VPS_USER}@${VPS_HOST} "mkdir -p ${VPS_PATH}"

# Copiar JAR al VPS
scp target/${JAR_NAME} ${VPS_USER}@${VPS_HOST}:${VPS_PATH}/

echo "✅ Archivo subido exitosamente!"
echo ""
echo "🔄 Paso 3: Reiniciando servicio en VPS..."

# Ejecutar comandos en VPS para reiniciar el servicio
ssh ${VPS_USER}@${VPS_HOST} << 'ENDSSH'
cd /root/mediapp-backend

# Detener el servicio si está corriendo
if systemctl is-active --quiet mediapp-backend; then
    echo "Deteniendo servicio..."
    systemctl stop mediapp-backend
fi

# Reiniciar el servicio
echo "Iniciando servicio..."
systemctl start mediapp-backend
systemctl enable mediapp-backend

# Verificar estado
sleep 3
if systemctl is-active --quiet mediapp-backend; then
    echo "✅ Servicio iniciado correctamente"
    systemctl status mediapp-backend --no-pager
else
    echo "❌ Error: El servicio no pudo iniciarse"
    journalctl -u mediapp-backend -n 50 --no-pager
    exit 1
fi
ENDSSH

echo ""
echo "======================================"
echo "✅ Deployment completado exitosamente!"
echo "======================================"
echo ""
echo "📊 Información del deployment:"
echo "   - VPS: ${VPS_HOST}"
echo "   - Ruta: ${VPS_PATH}"
echo "   - Servicio: ${SERVICE_NAME}"
echo ""
echo "🌐 URLs disponibles:"
echo "   - API: http://${VPS_HOST}:8080"
echo "   - Swagger UI: http://${VPS_HOST}:8080/swagger-ui.html"
echo "   - Health Check: http://${VPS_HOST}:8080/actuator/health"
echo ""
echo "📝 Comandos útiles:"
echo "   - Ver logs: ssh ${VPS_USER}@${VPS_HOST} 'journalctl -u ${SERVICE_NAME} -f'"
echo "   - Estado: ssh ${VPS_USER}@${VPS_HOST} 'systemctl status ${SERVICE_NAME}'"
echo "   - Reiniciar: ssh ${VPS_USER}@${VPS_HOST} 'systemctl restart ${SERVICE_NAME}'"
echo ""
