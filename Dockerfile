# Etapa de construcción
FROM adoptopenjdk:21-jdk-hotspot as build

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia solo el archivo POM para descargar dependencias
COPY pom.xml .

# Descarga dependencias usando Maven
RUN ./mvnw dependency:go-offline

# Copia el código fuente
COPY src ./src

# Compila la aplicación
RUN ./mvnw package

# Etapa de ejecución
FROM adoptopenjdk:21-jre-hotspot

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia solo los archivos necesarios desde la etapa de construcción
COPY --from=build /app/target/soporte-0.0.1-SNAPSHOT.jar .

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "target/soporte-0.0.1-SNAPSHOT.jar"]
