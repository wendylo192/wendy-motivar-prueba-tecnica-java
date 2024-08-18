# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Copia el JAR desde el directorio de construcción
COPY target/prices-api-0.0.1-SNAPSHOT.jar app.jar

# Configura el contenedor para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expone el puerto que la aplicación usará
EXPOSE 8080