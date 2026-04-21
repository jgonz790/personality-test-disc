#!/bin/bash
# ===========================================
# Deploy script VPS IONOS (Ubuntu/Debian)
# ===========================================
# Se ejecuta AUTOMATICAMENTE desde deploy-local.sh
# O puedes correrlo manualmente en tu VPS:
#   chmod +x /opt/disc-test/deploy.sh && /opt/disc-test/deploy.sh

set -e

DOMAIN="disc.cuatrocaminos.mx"
EMAIL="osvaldo.unet@gmail.com"
APP_DIR="/opt/disc-test"

echo "================================================"
echo "  DEPLOY EN VPS para $DOMAIN"
echo "================================================"

echo ""
echo "=== 1. Instalando dependencias ==="
apt-get update -qq
apt-get install -y docker-compose nginx certbot python3-certbot-nginx

systemctl enable docker
systemctl start docker

echo ""
echo "=== 2. Iniciando backend con Docker ==="
cd "$APP_DIR"
docker-compose down 2>/dev/null || true
docker-compose up -d --build

echo ""
echo "=== 3. Verificando que el backend responde ==="
sleep 12
STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/questions || echo "000")
if [ "$STATUS" = "200" ]; then
  echo "Backend OK (HTTP $STATUS)"
else
  echo "ADVERTENCIA: Backend retorno HTTP $STATUS - revisa los logs:"
  echo "  docker logs disc-personality-test"
fi

echo ""
echo "=== 4. Configurando Nginx ==="
cp "$APP_DIR/nginx-disc-test.conf" /etc/nginx/sites-available/disc-test
ln -sf /etc/nginx/sites-available/disc-test /etc/nginx/sites-enabled/disc-test
rm -f /etc/nginx/sites-enabled/default

nginx -t && systemctl reload nginx
echo "Nginx configurado OK"

echo ""
echo "=== 5. Certificado SSL con Let's Encrypt ==="
certbot --nginx -d "$DOMAIN" \
  --non-interactive \
  --agree-tos \
  --email "$EMAIL" \
  --redirect
echo "SSL configurado OK"

echo ""
echo "================================================"
echo "  DEPLOY COMPLETO!"
echo "  App en: https://$DOMAIN"
echo ""
echo "  Comandos utiles en el VPS:"
echo "  - Ver logs backend: docker logs disc-personality-test"
echo "  - Reiniciar backend: docker-compose -f $APP_DIR/docker-compose.yml restart"
echo "  - Estado nginx: systemctl status nginx"
echo "================================================"
