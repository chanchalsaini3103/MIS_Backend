# Start from an OpenJDK 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the jar file (after build)
COPY target/*.jar app.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
