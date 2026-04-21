# Contexto para Claude: Deploy DISC Personality Test en IONOS con Plesk

## TU MISION
Ayudar a desplegar esta app paso a paso en un VPS de IONOS que tiene **Plesk** como panel de control.
La meta final es que la app corra en **https://disc.cuatrocaminos.mx**

---

## ESTADO ACTUAL DEL PROYECTO

- **Localhost funcionó correctamente** en la sesión anterior
- Los archivos de deploy YA EXISTEN en el proyecto:
  - `deploy-local.sh` — script para correr desde la máquina Windows (sube archivos + ejecuta deploy)
  - `deploy.sh` — script que corre en el VPS (instala dependencias, Docker, nginx, SSL)
  - `nginx-disc-test.conf` — config de nginx para servir frontend + proxy al backend
  - `Dockerfile` — imagen Docker del backend Spring Boot
  - `docker-compose.yml` — orquesta el contenedor del backend

---

## EL PROBLEMA IMPORTANTE: PLESK cambia todo

El VPS de IONOS tiene **Plesk**, que gestiona su propio nginx/Apache.
**No se pueden editar archivos nginx manualmente** como en un servidor sin panel.
Plesk tiene su propia forma de hacer las cosas.

La estrategia correcta con Plesk es:
1. Crear el subdominio `disc.cuatrocaminos.mx` desde el panel de Plesk
2. Plesk crea automáticamente la config de nginx/Apache para ese subdominio
3. El backend (Spring Boot en Docker) corre en un puerto interno (8080)
4. En Plesk se configura un **Reverse Proxy** para que ese subdominio apunte al puerto 8080
5. SSL se gestiona desde Plesk (tiene integración con Let's Encrypt)

---

## ARQUITECTURA DE LA APP

```
[Usuario] → https://disc.cuatrocaminos.mx
                    ↓
            [Plesk / Nginx]
           /               \
  Archivos estáticos    /api/* → proxy
  Angular (frontend)    localhost:8080
                            ↓
                    [Docker Container]
                    Spring Boot Java 17
                    disc-test-1.0.0.jar
```

---

## STACK TÉCNICO

### Backend (Spring Boot)
- **Framework:** Spring Boot 3.2.5
- **Java:** 17 (imagen Docker: eclipse-temurin:17-jre-alpine)
- **Puerto:** 8080
- **JAR:** `backend/target/disc-test-1.0.0.jar`
- **CORS:** `@CrossOrigin(origins = "*")` — acepta cualquier origen

### API Endpoints
```
GET  /api/questions          → Lista de preguntas del test
POST /api/evaluate           → Evalúa respuestas y devuelve resultado
GET  /api/personalities      → Lista todas las personalidades
GET  /api/personalities/{key}→ Info de una personalidad específica
```

### Frontend (Angular)
- **Framework:** Angular (versión reciente)
- **Build:** `frontend/dist/frontend/browser/` — archivos listos para servir
- **API URL:** usa `/api` como URL relativa — funciona perfecto con proxy nginx
- **Archivos del build:**
  - `index.html`
  - `main-ABUEWP4P.js`
  - `styles-OBGUUP2U.css`
  - `favicon.ico`

---

## DATOS DEL SERVIDOR

- **Proveedor:** IONOS VPS
- **Panel:** Plesk (versión desconocida, preguntar al usuario)
- **OS:** Linux (Ubuntu o Debian, confirmar con usuario)
- **Docker:** YA INSTALADO ✓
- **Dominio principal:** cuatrocaminos.mx
- **Subdominio objetivo:** disc.cuatrocaminos.mx

---

## PERSONALIDADES DEL TEST

El test mide 4 tipos de personalidad DISC adaptados:
- **LEON (D)** — Dominante, directo, decidido
- **NUTRIA (I)** — Influyente, sociable, entusiasta  
- **LABRADOR DORADO (S)** — Estable, paciente, leal
- **CASTOR (C)** — Concienzudo, analítico, preciso

---

## ARCHIVOS CLAVE DEL PROYECTO

```
personality-test-disc/
├── deploy-local.sh          ← Correr desde Windows Git Bash
├── deploy.sh                ← Se ejecuta en el VPS
├── nginx-disc-test.conf     ← Config nginx (puede que Plesk no la use directamente)
├── Dockerfile               ← Backend Docker image
├── docker-compose.yml       ← Backend service
├── CLAUDE_CONTEXT.md        ← ESTE ARCHIVO
├── backend/
│   ├── pom.xml
│   ├── target/
│   │   └── disc-test-1.0.0.jar  ← JAR compilado listo
│   └── src/main/java/com/personality/disc/
│       ├── controller/PersonalityController.java
│       ├── service/PersonalityService.java
│       └── model/ (QuestionRound, TestAnswers, TestResult, etc.)
└── frontend/
    ├── src/app/services/personality.ts  ← usa apiUrl = '/api'
    └── dist/frontend/browser/           ← BUILD LISTO para producción
```

---

## PASOS A SEGUIR (en orden)

### PASO 1: Subir el backend a VPS
Subir el JAR y los archivos de Docker al VPS:
```bash
# Desde Windows Git Bash, en la carpeta del proyecto
scp backend/target/disc-test-1.0.0.jar \
    Dockerfile \
    docker-compose.yml \
    root@IP_VPS:/opt/disc-test/
```

### PASO 2: Levantar backend con Docker en el VPS
```bash
ssh root@IP_VPS
mkdir -p /opt/disc-test
cd /opt/disc-test
docker-compose up -d --build
# Verificar que corre:
curl http://localhost:8080/api/questions
```

### PASO 3: Crear subdominio en Plesk
1. Login a Plesk (https://IP_VPS:8443)
2. Ir a "Websites & Domains"
3. Clic en "Add Subdomain"
4. Nombre: `disc`, Dominio: `cuatrocaminos.mx`
5. Plesk crea la carpeta raíz automáticamente (algo como `/var/www/vhosts/cuatrocaminos.mx/disc.cuatrocaminos.mx/httpdocs/`)

### PASO 4: Subir frontend a Plesk
Subir los archivos del build de Angular a la carpeta httpdocs del subdominio:
```bash
scp -r frontend/dist/frontend/browser/. \
    root@IP_VPS:/var/www/vhosts/cuatrocaminos.mx/disc.cuatrocaminos.mx/httpdocs/
```

### PASO 5: Configurar Reverse Proxy en Plesk
En Plesk para el subdominio disc.cuatrocaminos.mx:
1. Ir a "Apache & nginx Settings" del subdominio
2. En la sección "Additional nginx directives" agregar:
```nginx
location /api/ {
    proxy_pass http://localhost:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
}
```
3. Guardar y aplicar

### PASO 6: SSL con Let's Encrypt en Plesk
1. En Plesk, ir al subdominio disc.cuatrocaminos.mx
2. Buscar "SSL/TLS Certificates"
3. Instalar certificado gratuito de Let's Encrypt
4. Marcar "Redirect HTTP to HTTPS"

---

## POSIBLES PROBLEMAS Y SOLUCIONES

| Problema | Solución |
|----------|----------|
| Docker no levanta | `docker logs disc-personality-test` para ver el error |
| Puerto 8080 ocupado | `lsof -i :8080` para ver qué lo usa |
| Plesk no muestra "Additional nginx directives" | Activar el modo nginx en Plesk: Tools & Settings → Web Server Settings |
| El frontend carga pero /api da 502 | El Docker no está corriendo: `docker ps` |
| SSL falla | Verificar que el subdominio DNS ya apunta al VPS (puede tardar hasta 24h) |
| Carpeta httpdocs diferente | `find /var/www -name "httpdocs" -path "*disc*"` |

---

## COMANDOS ÚTILES EN EL VPS

```bash
# Ver contenedores Docker corriendo
docker ps

# Ver logs del backend
docker logs disc-personality-test

# Reiniciar backend
cd /opt/disc-test && docker-compose restart

# Ver uso de puertos
ss -tlnp | grep 8080

# Reiniciar nginx de Plesk
/etc/init.d/nginx restart
# O con systemctl:
systemctl restart nginx

# Ver qué versión de Plesk tienes
cat /usr/local/psa/version
```

---

## NOTAS IMPORTANTES PARA CLAUDE

1. **El usuario habla español** — responde siempre en español
2. **Plesk es el panel** — no dar instrucciones de editar archivos nginx manualmente a menos que el usuario confirme que tiene acceso root SIN Plesk gestionando nginx
3. **Docker ya está instalado** — no incluir pasos de instalación de Docker
4. **El JAR ya está compilado** — `disc-test-1.0.0.jar` listo en `backend/target/`
5. **El frontend ya está compilado** — archivos en `frontend/dist/frontend/browser/`
6. **Ir paso a paso** — esperar confirmación del usuario en cada paso antes de continuar
7. **Localhost funcionó** — el código está correcto, el problema es solo el deploy/configuración del servidor
8. **CORS ya está configurado** — `@CrossOrigin(origins = "*")` en el backend
9. **La URL del API es relativa** — el frontend usa `/api` sin hardcodear dominio, funciona con cualquier proxy

---

## CÓMO USAR ESTE ARCHIVO CON CLAUDE

Pega este mensaje al inicio de una nueva conversación con Claude:

---
Lee el archivo CLAUDE_CONTEXT.md que te voy a dar y ayúdame a desplegar mi app paso a paso.
[Pega aquí el contenido de este archivo]

Mi IP del VPS de IONOS es: [PON TU IP AQUÍ]
Ya tengo acceso SSH como root.
¿Por qué paso empezamos?
---
