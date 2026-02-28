# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port
EXPOSE 8087

# Set environment variables (can be overridden during container startup)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/hrsupport_suza
ENV SPRING_DATASOURCE_USERNAME=rahma
ENV SPRING_DATASOURCE_PASSWORD=12345

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD java -version || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
