FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

FROM build AS test
RUN mvn verify

FROM build AS package
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-jammy AS final
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=package --chown=springuser:spring /app/${JAR_FILE} app.jar
# Activate the 'docker' profile
ENV SPRING_PROFILES_ACTIVE=docker
ENV LDAP_HOST=ldap
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
