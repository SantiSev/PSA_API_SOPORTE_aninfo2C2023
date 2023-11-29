# Imagen base de Java y Maven
FROM adoptopenjdk:21-jdk-hotspot

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml para descargar las dependencias
COPY pom.xml .

# Descargar las dependencias del proyecto
RUN mvn dependency:go-offline -B

# Copiar el resto de los archivos del proyecto
COPY src ./src

# Empaquetar la aplicación en un archivo JAR
RUN mvn package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación Java
CMD ["java", "-jar", "target/Proyectos-0.0.1-SNAPSHOT.jar"]