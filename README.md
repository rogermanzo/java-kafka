# SaludApp Microservices

Arquitectura de microservicios para SaludApp con Spring Boot 3.x, Spring Cloud, Kafka y Redis.

## 🏗️ Estructura del Proyecto

```
java-kafka-microservices/
├── pom.xml                    # POM padre
├── eureka-server/             # Service Discovery
│   ├── pom.xml
│   └── src/main/java/com/api/saludapp/eureka/
├── api-gateway/              # API Gateway
│   ├── pom.xml
│   └── src/main/java/com/api/saludapp/gateway/
└── user-service/              # User Management Service
    ├── pom.xml
    └── src/main/java/com/api/saludapp/user/
```

## 🚀 Servicios

### 1. Eureka Server (Puerto 8761)
- **Función**: Service Discovery
- **URL**: http://localhost:8761
- **Dependencias**: Solo Eureka Server

### 2. API Gateway (Puerto 8080)
- **Función**: Punto de entrada único
- **URL**: http://localhost:8080
- **Dependencias**: Spring Cloud Gateway + Eureka Client

### 3. User Service (Puerto 8081)
- **Función**: Gestión de usuarios
- **URL**: http://localhost:8081
- **Dependencias**: Web + JPA + Redis + Kafka + Eureka Client

## 📋 Orden de Ejecución

### 1. Eureka Server
```bash
cd eureka-server
mvn clean package -DskipTests
java -jar target/eureka-server-1.0.0-SNAPSHOT.jar
```

### 2. User Service
```bash
cd user-service
mvn clean package -DskipTests
java -jar target/user-service-1.0.0-SNAPSHOT.jar
```

### 3. API Gateway
```bash
cd api-gateway
mvn clean package -DskipTests
java -jar target/api-gateway-1.0.0-SNAPSHOT.jar
```

## 🔧 Comandos Útiles

### Compilar todos los servicios
```bash
mvn clean package -DskipTests
```

### Ejecutar desde el directorio raíz
```bash
# Eureka Server
java -jar eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar

# User Service
java -jar user-service/target/user-service-1.0.0-SNAPSHOT.jar

# API Gateway
java -jar api-gateway/target/api-gateway-1.0.0-SNAPSHOT.jar
```

## 🌐 URLs de Acceso

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **User Service**: http://localhost:8081
- **Users API**: http://localhost:8080/api/users (a través del Gateway)

## 🛠️ Tecnologías

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Spring Cloud**: 2024.0.0
- **Base de datos**: MySQL
- **Cache**: Redis
- **Messaging**: Apache Kafka
- **Build**: Maven
