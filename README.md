## Practico DevOps - Cafe API

Proyecto de practica DevOps que expone una API de cafeteria escrita con Spring Boot (Java 17). Permite gestionar productos, tomar ordenes y publicar metricas en formato Prometheus. El repositorio incluye contenedores para Prometheus y Grafana y manifiestos de Kubernetes para desplegar toda la solucion.

- Aplicacion: `demo/cafe-api` (Spring Boot, Actuator, Micrometer)
- Observabilidad: Prometheus + Grafana con tableros configurables
- Despliegues: Docker Compose para desarrollo local y manifiestos `k8s/` para Kubernetes

### Requisitos previos

- Git
- Java 17
- Docker y Docker Compose
- kubectl y acceso a un cluster
- Postman (recomendado)

### Ejecucion directa con Maven

1. Instalar dependencias (solo la primera vez): `./mvnw dependency:go-offline` (Windows PowerShell: `.\mvnw.cmd dependency:go-offline`).
2. Compilar y ejecutar la API: `./mvnw spring-boot:run` (`.\mvnw.cmd spring-boot:run` en Windows).
3. API disponible en `http://localhost:8080`.
4. Detener con `Ctrl + C`.

### Levantar todo con Docker Compose

1. Construir la imagen y levantar los contenedores:
   ```bash
   docker compose up --build
   ```
2. Servicios disponibles:
   - API: `http://localhost:8080`
   - Prometheus: `http://localhost:9090/targets`
   - Grafana: `http://localhost:3000` (usuario: `admin`, password: `admin`)
3. Validar metricas de la API en `http://localhost:8080/actuator/prometheus`.
4. Detener y limpiar: `docker compose down`.

### Despliegue en Kubernetes (opcional)

1. Asegurarse de tener una imagen local `cafe-api:latest`. Si se trabaja en un cluster local (por ejemplo kind o minikube):
   ```bash
   docker build -t cafe-api:latest demo/cafe-api
   ```
   Para clusters remotos, publicar la imagen en un registry accesible y actualizar los manifests si es necesario.
2. Crear namespace y recursos:
   ```bash
   kubectl apply -f k8s/namespace.yaml
   kubectl apply -f k8s/deployment.yaml
   kubectl apply -f k8s/service.yaml
   kubectl apply -f k8s/prometheus-deployment.yaml
   kubectl apply -f k8s/grafana-deployment.yaml
   ```
3. Endpoints expuestos con NodePort (ajustar segun el entorno):
   - API: `http://<node-ip>:30080`
   - Prometheus: `http://<node-ip>:30090`
   - Grafana: `http://<node-ip>:30300`
4. Eliminar recursos cuando ya no se necesiten:
   ```bash
   kubectl delete -f k8s/grafana-deployment.yaml
   kubectl delete -f k8s/prometheus-deployment.yaml
   kubectl delete -f k8s/service.yaml
   kubectl delete -f k8s/deployment.yaml
   kubectl delete -f k8s/namespace.yaml
   ```

### Endpoints principales

- `GET /products`: lista inicial de productos.
- `POST /products`: crea un producto (JSON con `id`, `nombre`, `precio`).
- `PUT /products/{id}` y `DELETE /products/{id}`: actualiza o elimina productos.
- `GET /orders`: lista ordenes generadas.
- `POST /orders`: registra una orden nueva con items (`productId`, `cantidad`).
- `GET /actuator/prometheus`: metricas para Prometheus.

# Recursos recomendados

Postman: https://poseagus15-5718737.postman.co/workspace/Agustin-Pose's-Workspace~05cb38f6-23ba-46d3-87d5-9375f6de3ff1/collection/48467642-5f139450-40bf-45b4-a4aa-1f69ed3bcc17?action=share&creator=48467642

### Datos de contacto

Proyecto desarrollado por Mateo Hernandez y Agustin Pose.
