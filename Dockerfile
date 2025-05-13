# Use Maven to build the application
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# --------------------------------------

# Use lightweight JDK image to run the app
FROM eclipse-temurin:17-jdk

# Set working directory in container
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
