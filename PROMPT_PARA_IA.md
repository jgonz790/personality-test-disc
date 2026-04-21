Hola, necesito que me ayudes a desplegar una aplicación en mi VPS de IONOS paso a paso.
Por favor guíame con pasos simples, espera mi confirmación antes de continuar al siguiente paso,
y responde siempre en español.

---

## QUÉ ES LA APP

Test de personalidad DISC (León, Nutria, Labrador Dorado, Castor).
- **Frontend:** Angular → hace peticiones a `/api` (URL relativa, sin dominio hardcodeado)
- **Backend:** Spring Boot 3.2.5, Java 17, corre en puerto 8080
- **Endpoints:** GET /api/questions, POST /api/evaluate, GET /api/personalities
- **CORS:** configurado con `@CrossOrigin(origins = "*")` — acepta cualquier origen
- **El app corrió bien en localhost** — el código está correcto, solo falta el deploy

## DÓNDE ESTÁ EL CÓDIGO

Repo en GitHub: https://github.com/jgonz790/personality-test-disc

Estructura relevante:
```
personality-test-disc/
├── Dockerfile                        ← imagen Docker del backend (Java 17)
├── docker-compose.yml                ← levanta backend en puerto 8080
├── nginx-disc-test.conf              ← config nginx (proxy /api/ + frontend estático)
├── deploy.sh                         ← script que instala todo en el VPS
├── deploy-local.sh                   ← script para subir archivos desde Windows
├── backend/
│   └── target/disc-test-1.0.0.jar   ← JAR compilado listo para producción
└── frontend/
    └── dist/frontend/browser/        ← build Angular listo (index.html, main.js, styles.css)
```

## MI SERVIDOR

- **Proveedor:** IONOS VPS
- **Panel:** Plesk (importante: Plesk gestiona nginx, NO se editan archivos manualmente)
- **OS:** Linux (Ubuntu o Debian)
- **Docker:** YA está instalado ✓
- **Acceso:** SSH como root
- **Dominio principal:** cuatrocaminos.mx
- **Meta:** que la app corra en https://disc.cuatrocaminos.mx

## LO QUE HAY QUE HACER (en orden)

1. Clonar el repo del VPS y compilar, O subir los archivos desde mi PC Windows con scp
2. Levantar el backend con `docker-compose up -d` en `/opt/disc-test/`
3. Verificar que el backend responde: `curl http://localhost:8080/api/questions`
4. En Plesk: crear subdominio `disc.cuatrocaminos.mx`
5. Subir los archivos del frontend Angular a la carpeta httpdocs del subdominio en Plesk
6. En Plesk: configurar Reverse Proxy para que `/api/` apunte a `localhost:8080`
7. En Plesk: activar SSL con Let's Encrypt para `disc.cuatrocaminos.mx`

## CÓMO FUNCIONA LA ARQUITECTURA

```
Usuario → https://disc.cuatrocaminos.mx
                    ↓
            Plesk / Nginx
           /              \
Archivos estáticos      /api/* → proxy
Angular (frontend)      localhost:8080
  (index.html,               ↓
   main.js, etc.)     Docker Container
                      Spring Boot JAR
```

## LO QUE PLESK CAMBIA

Con Plesk NO se editan archivos nginx directamente.
En cambio, en el panel de Plesk:
- Se crea el subdominio → Plesk genera la config automáticamente
- Para el proxy se usa "Apache & nginx Settings" → "Additional nginx directives"
- SSL se instala desde "SSL/TLS Certificates" con el botón de Let's Encrypt

## COMANDOS ÚTILES EN EL VPS

```bash
# Ver si Docker está corriendo
docker ps

# Ver logs del backend
docker logs disc-personality-test

# Reiniciar backend
cd /opt/disc-test && docker-compose restart

# Ver puertos en uso
ss -tlnp | grep 8080

# Versión de Plesk
cat /usr/local/psa/version

# Buscar carpeta httpdocs del subdominio
find /var/www -name "httpdocs" -path "*disc*" 2>/dev/null
```

## POSIBLES PROBLEMAS

| Problema | Solución |
|----------|----------|
| Backend no levanta | `docker logs disc-personality-test` |
| Puerto 8080 ocupado | `lsof -i :8080` |
| Plesk no muestra "Additional nginx directives" | Activar modo nginx: Tools & Settings → Web Server Settings |
| Frontend carga pero /api da 502 | Docker no está corriendo: `docker ps` |
| SSL falla | Verificar que DNS del subdominio ya apunta al VPS |
| No sé dónde poner los archivos del frontend | `find /var/www -name "httpdocs" -path "*disc*"` |

---

Mi IP del VPS es: [ESCRIBE TU IP AQUÍ ANTES DE PEGAR ESTO]
Ya tengo acceso SSH como root.

¿Por qué paso empezamos?
