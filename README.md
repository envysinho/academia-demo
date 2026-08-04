# academia-demo

Sistema web base para gestion academica, construido con React, Java 21, Spring Boot y PostgreSQL.

## Estructura

- `frontend/`: aplicacion React con Vite.
- `backend/`: API REST con Spring Boot 3, Java 21, JPA y PostgreSQL.
- `docker-compose.yml`: servicio local de PostgreSQL.

## Requisitos

- Java 21
- Maven 3.9+
- Node.js 20+
- npm
- Docker o Podman para PostgreSQL

## Primer arranque

1. Levantar la base de datos:

   ```bash
   docker compose up -d db
   ```

2. Ejecutar el backend:

   ```bash
   cd backend
   mvn spring-boot:run
   ```

3. Ejecutar el frontend:

   ```bash
   cd frontend
   npm run dev
   ```

## URLs locales

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8081`
- Health API: `http://localhost:8081/api/health`

## Configuracion

El backend lee estas variables de entorno:

- `DB_URL`: URL JDBC de PostgreSQL. Por defecto `jdbc:postgresql://localhost:5433/academia_demo`.
- `DB_USERNAME`: usuario de PostgreSQL. Por defecto `academia`.
- `DB_PASSWORD`: password de PostgreSQL. Por defecto `academia`.
- `APP_CORS_ALLOWED_ORIGINS`: origenes permitidos para CORS. Por defecto `http://localhost:5173`.
- `SERVER_PORT`: puerto HTTP del backend. Por defecto `8081`.

El frontend puede usar `VITE_API_URL` para cambiar la URL del API. Por defecto usa `http://localhost:8081/api`.

## Funcionalidades iniciales

- Listar y registrar estudiantes.
- Listar y registrar cursos.
- Matricular estudiantes en cursos.
- Dashboard simple con metricas academicas.
