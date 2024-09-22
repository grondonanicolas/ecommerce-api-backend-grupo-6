# Usar una imagen base con Java 17
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Instalar Maven usando apt-get
RUN apt-get update && apt-get install -y maven

# Copiar el archivo POM y descargar dependencias (caché en capas de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Montar el código fuente de la aplicación
COPY src ./src

# Comando para ejecutar la aplicación con Spring Boot DevTools
CMD ["mvn", "spring-boot:run"]


#docker run -v "$(pwd)/src:/app/src" \
#-v "$(pwd)/pom.xml:/app/pom.xml" \
#-p 8080:8080 \
#-w /app \
#-it springboot-app
