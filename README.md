# API de Gestión de Presupuestos 💰

Una API REST desarrollada con Spring Boot para la gestión completa de presupuestos de proyectos. Permite crear, consultar, actualizar y eliminar presupuestos con validaciones robustas y manejo de excepciones.

## 🚀 Características

- **CRUD completo** para gestión de presupuestos
- **Validaciones de entrada** con Bean Validation
- **Manejo global de excepciones** personalizado
- **Documentación automática** con Swagger/OpenAPI 3
- **Arquitectura por capas** bien estructurada
- **DTOs separados** para operaciones específicas
- **Base de datos PostgreSQL**

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **Spring Boot Actuator**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Bean Validation (Jakarta)**
- **SpringDoc OpenAPI 3 (v2.0.4)**
- **Spring Boot DevTools**

## 📋 Funcionalidades

### Estados de Presupuesto
- `PENDIENTE` - Presupuesto en espera de revisión
- `APROBADO` - Presupuesto aprobado
- `RECHAZADO` - Presupuesto rechazado

### Operaciones Disponibles
- ✅ Crear nuevo presupuesto
- ✅ Listar todos los presupuestos
- ✅ Obtener presupuesto por ID
- ✅ Actualizar presupuesto existente
- ✅ Eliminar presupuesto

## 🏗️ Estructura del Proyecto

```
src/main/java/com/api/presupuesto/api_presupuesto/
├── config/
│   └── SwaggerConfig.java          # Configuración de Swagger
├── controller/
│   └── PresupuestoController.java  # Controlador REST
├── dto/
│   ├── PresupuestoCreateDTO.java   # DTO para creación
│   ├── PresupuestoDTO.java         # DTO de respuesta
│   └── PresupuestoUpdateDTO.java   # DTO para actualización
├── exception/
│   ├── ManejadorGlobalExcepciones.java         # Manejo global de errores
│   └── PresupuestoNoEncontradoException.java   # Excepción personalizada
├── mapper/
│   └── PresupuestoMapper.java      # Mapeo entre entidades y DTOs
├── model/
│   └── Presupuesto.java           # Entidad JPA
├── repository/
│   └── PresupuestoRepository.java # Repositorio JPA
└── service/
    ├── PresupuestoService.java     # Interfaz del servicio
    └── impl/
        └── PresupuestoServiceImpl.java # Implementación del servicio
```

## ⚙️ Instalación y Configuración

### Dependencias Maven

El proyecto utiliza las siguientes dependencias principales:

```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <!-- Base de datos -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Documentación -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.0.4</version>
    </dependency>
    
    <!-- Herramientas -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/api-gestion-presupuestos.git
cd api-gestion-presupuestos
```

### 2. Configurar base de datos PostgreSQL
Crear una base de datos en PostgreSQL:
```sql
CREATE DATABASE db_presupuesto;
```

### 3. Configurar application.properties
El archivo `application.properties` debe contener:

```properties
spring.application.name=api-presupuesto

# Base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/db_presupuesto
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña

# Configuración JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

> **Nota**: Asegúrate de cambiar `tu_contraseña` por tu contraseña real de PostgreSQL.

### 4. Instalar dependencias y ejecutar
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación de la API

### Swagger UI
Una vez ejecutada la aplicación, puedes acceder a la documentación interactiva:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

### Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST   | `/api/presupuestos` | Crear nuevo presupuesto |
| GET    | `/api/presupuestos` | Listar todos los presupuestos |
| GET    | `/api/presupuestos/{id}` | Obtener presupuesto por ID |
| PUT    | `/api/presupuestos/{id}` | Actualizar presupuesto |
| DELETE | `/api/presupuestos/{id}` | Eliminar presupuesto |

## 🔧 Ejemplos de Uso

### Crear un presupuesto
```bash
curl -X POST http://localhost:8080/api/presupuestos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Presupuesto Proyecto A",
    "fecha": "2024-12-01",
    "montoTotal": 15000.50,
    "estado": "PENDIENTE"
  }'
```

### Obtener todos los presupuestos
```bash
curl -X GET http://localhost:8080/api/presupuestos
```

### Actualizar un presupuesto
```bash
curl -X PUT http://localhost:8080/api/presupuestos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Presupuesto Proyecto A - Actualizado",
    "fecha": "2024-12-01",
    "montoTotal": 18000.00,
    "estado": "APROBADO"
  }'
```

## ✅ Validaciones

El sistema incluye las siguientes validaciones:

- **Nombre**: Obligatorio, no puede estar vacío
- **Fecha**: Obligatoria, debe ser una fecha válida
- **Monto Total**: Obligatorio, debe ser mayor o igual a 0
- **Estado**: Obligatorio, debe ser uno de: `PENDIENTE`, `APROBADO`, `RECHAZADO`

## 🚨 Manejo de Errores

La API maneja los siguientes tipos de errores:

- **404 Not Found**: Presupuesto no encontrado
- **400 Bad Request**: Errores de validación
- **500 Internal Server Error**: Errores del servidor

Ejemplo de respuesta de error:
```json
{
  "nombre": "El nombre es obligatorio",
  "montoTotal": "El monto total debe ser mayor o igual a 0"
}
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Yurgen Prado Lopez**
- Email: yurgen2616@hotmail.com
- GitHub: [@tu-usuario](https://github.com/tu-usuario)
