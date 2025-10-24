#!/bin/bash

# Script para ver logs en tiempo real del backend en VPS

echo "📋 Conectando a logs de MediApp Backend..."
echo "   Presiona Ctrl+C para salir"
echo ""

ssh root@56.125.172.86 'journalctl -u mediapp-backend -f'
