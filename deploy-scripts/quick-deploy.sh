#!/bin/bash

# Script rápido de deployment - Solo copia y reinicia
# Para usar cuando ya tienes todo configurado en el VPS

set -e

echo "🚀 Deployment rápido de MediApp Backend"
echo ""

# Compilar
echo "📦 Compilando..."
./mvnw clean package -DskipTests

# Subir
echo "📤 Subiendo a VPS..."
scp target/citas-backend-0.0.1-SNAPSHOT.jar root@56.125.172.86:/root/mediapp-backend/

# Reiniciar
echo "🔄 Reiniciando servicio..."
ssh root@56.125.172.86 'systemctl restart mediapp-backend && systemctl status mediapp-backend --no-pager'

echo ""
echo "✅ Deployment completado!"
echo "🌐 Swagger UI: http://56.125.172.86:8080/swagger-ui.html"
