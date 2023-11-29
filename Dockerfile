# Imagen base de OpenJDK 21 con Maven
FROM adoptopenjdk:21-jdk-hotspot AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml para descargar las dependencias
COPY pom.xml .

# Copiar el resto de los archivos del proyecto
COPY src ./src

# Empaquetar la aplicación en un archivo JAR
RUN ./mvnw package -DskipTests

# Imagen base para la ejecución
FROM adoptopenjdk:21-jre-hotspot

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar solo los archivos necesarios desde la etapa de construcción
COPY --from=build /app/target/soporte-0.0.1-SNAPSHOT.jar .

# Comando para ejecutar la aplicación Java
CMD ["java", "-jar", "soporte-0.0.1-SNAPSHOT.jar"]
