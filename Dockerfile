# =============================================
# Build Stage - Uses a full JDK and Maven image
# =============================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .

# Download dependencies. This layer is only rebuilt when pom.xml changes.
RUN mvn dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Run tests. The build will fail if any tests fail.
RUN mvn test

# Build the application, creating the executable JAR. Skip tests as they've already run.
RUN mvn package -DskipTests

# =============================================
# Final Stage - Uses a lightweight JRE image
# =============================================
FROM eclipse-temurin:17-jre-jammy

# Create a dedicated, non-root user and group for security
RUN addgroup --system spring && adduser --system --ingroup spring springuser

# Set the dedicated user to run the application
USER springuser

# Set the working directory
WORKDIR /app

# Argument to define the path to the JAR file from the build stage
ARG JAR_FILE=target/*.jar

# Copy the built JAR from the 'build' stage into the final image
COPY --from=build --chown=springuser:spring /app/${JAR_FILE} app.jar

# Expose the port the application runs on
EXPOSE 8080

# Health check to ensure the application is running and healthy
# Requires the Spring Boot Actuator dependency in your project
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Command to run the application
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
