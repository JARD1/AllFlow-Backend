# Usamos una imagen de Java 17 (asegúrate que sea la versión de tu proyecto)
FROM eclipse-temurin:17-jdk-alpine

# Creamos el directorio de trabajo
WORKDIR /app

# Copiamos el archivo JAR generado desde la carpeta target
COPY target/*.jar app.jar

# Exponemos el puerto de tu aplicación (9090)
EXPOSE 9090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]