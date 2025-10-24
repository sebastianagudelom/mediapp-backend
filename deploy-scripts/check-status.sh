#!/bin/bash

# Script para verificar el estado del servicio en VPS

echo "🔍 Verificando estado de MediApp Backend en VPS..."
echo ""

ssh root@56.125.172.86 << 'ENDSSH'
echo "📊 Estado del servicio:"
systemctl status mediapp-backend --no-pager
echo ""
echo "💾 Uso de memoria:"
ps aux | grep java | grep -v grep
echo ""
echo "🌐 Puerto 8080:"
netstat -tulpn | grep :8080 || ss -tulpn | grep :8080
echo ""
echo "📝 Últimas líneas del log:"
journalctl -u mediapp-backend -n 20 --no-pager
ENDSSH
