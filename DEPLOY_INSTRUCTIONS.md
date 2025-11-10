# 🚀 Instrucciones para Deployment del Backend

## Opción 1: Usar el Script Automático (Recomendado)

```bash
cd /Users/sebastianagudelo/Documents/Uniquindio/citas-backend
chmod +x deploy-scripts/deploy.sh
./deploy-scripts/deploy.sh
```

## Opción 2: Deployment Manual Paso a Paso

### Paso 1: Compilar el proyecto

```bash
cd /Users/sebastianagudelo/Documents/Uniquindio/citas-backend
./mvnw clean package -DskipTests
```

Esto generará el archivo JAR en: `target/citas-backend-0.0.1-SNAPSHOT.jar`

### Paso 2: Verificar que el JAR se generó correctamente

```bash
ls -lh target/citas-backend-0.0.1-SNAPSHOT.jar
```

### Paso 3: Subir el JAR a la instancia EC2

```bash
scp -i ~/downloads/mediapp-key.pem \
  target/citas-backend-0.0.1-SNAPSHOT.jar \
  ubuntu@56.125.172.86:/opt/citas/app.jar
```

**Nota:** Si la ruta de la clave es diferente, ajusta `~/downloads/mediapp-key.pem` a tu ruta correcta.

### Paso 4: Conectarse a la instancia EC2 y reiniciar el servicio

```bash
ssh -i ~/downloads/mediapp-key.pem ubuntu@56.125.172.86
```

Una vez conectado, ejecuta:

```bash
# Detener el servicio
sudo systemctl stop citas

# Asegurar permisos correctos
sudo chown citasuser:citasuser /opt/citas/app.jar
sudo chmod 644 /opt/citas/app.jar

# Iniciar el servicio
sudo systemctl start citas

# Verificar estado
sudo systemctl status citas

# Ver logs si hay problemas
sudo journalctl -u citas -n 50 --no-pager
```

### Paso 5: Verificar que el servicio está corriendo

```bash
# Desde tu máquina local
curl http://56.125.172.86:8080/actuator/health
```

O visita en el navegador:
- API: http://56.125.172.86:8080
- Swagger UI: http://56.125.172.86:8080/swagger-ui.html
- Health Check: http://56.125.172.86:8080/actuator/health

## 🔧 Solución de Problemas

### Si el servicio no inicia:

```bash
ssh -i ~/downloads/mediapp-key.pem ubuntu@56.125.172.86
sudo journalctl -u citas -n 100 --no-pager
```

### Si el puerto 8080 está ocupado:

```bash
ssh -i ~/downloads/mediapp-key.pem ubuntu@56.125.172.86
sudo lsof -i :8080
# Si hay un proceso, detenerlo:
sudo kill -9 <PID>
```

### Si necesitas ver los logs en tiempo real:

```bash
ssh -i ~/downloads/mediapp-key.pem ubuntu@56.125.172.86
sudo journalctl -u citas -f
```

## 📝 Variables de Configuración

- **VPS_HOST:** 56.125.172.86
- **VPS_USER:** ubuntu
- **VPS_KEY:** ~/downloads/mediapp-key.pem (ajusta según tu configuración)
- **VPS_PATH:** /opt/citas
- **JAR_NAME:** app.jar (nombre final en el servidor)
- **SERVICE_NAME:** citas

## ✅ Verificación Post-Deployment

1. Verificar que el servicio está activo:
   ```bash
   ssh -i ~/downloads/mediapp-key.pem ubuntu@56.125.172.86 'systemctl status citas'
   ```

2. Probar el endpoint de health:
   ```bash
   curl http://56.125.172.86:8080/actuator/health
   ```

3. Probar un endpoint de la API:
   ```bash
   curl http://56.125.172.86:8080/api/especialidades
   ```

