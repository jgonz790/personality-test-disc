#!/bin/bash
# ===========================================
# Script LOCAL: sube archivos y despliega en VPS IONOS
# ===========================================
# Ejecuta esto desde tu máquina Windows (Git Bash)
# Uso: ./deploy-local.sh root@IP_DE_TU_VPS
#
# Ejemplo: ./deploy-local.sh root@88.99.123.45

set -e

if [ -z "$1" ]; then
  echo "ERROR: Falta la IP de tu VPS"
  echo "Uso: ./deploy-local.sh root@IP_DE_TU_VPS"
  echo "Ejemplo: ./deploy-local.sh root@88.99.123.45"
  exit 1
fi

VPS="$1"
REMOTE_DIR="/opt/disc-test"

echo "================================================"
echo "  DEPLOY disc.cuatrocaminos.mx -> $VPS"
echo "================================================"

echo ""
echo "=== 1. Creando directorio en VPS ==="
ssh "$VPS" "mkdir -p $REMOTE_DIR/frontend"

echo ""
echo "=== 2. Subiendo backend (.jar y config) ==="
scp backend/target/disc-test-1.0.0.jar \
    Dockerfile \
    docker-compose.yml \
    deploy.sh \
    nginx-disc-test.conf \
    "$VPS:$REMOTE_DIR/"

echo ""
echo "=== 3. Subiendo frontend (Angular build) ==="
scp -r frontend/dist/frontend/browser/. "$VPS:$REMOTE_DIR/frontend/"

echo ""
echo "=== 4. Ejecutando deploy en el VPS ==="
ssh "$VPS" "chmod +x $REMOTE_DIR/deploy.sh && $REMOTE_DIR/deploy.sh"

echo ""
echo "================================================"
echo "  LISTO!"
echo "  Tu app esta en: https://disc.cuatrocaminos.mx"
echo "================================================"
