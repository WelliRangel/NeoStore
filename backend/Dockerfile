# Etapa 1: Build do WAR com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Deploy do WAR no WildFly
FROM quay.io/wildfly/wildfly:35.0.1.Final-jdk21
COPY --from=build /app/target/neostore.war /opt/wildfly/standalone/deployments/neostore.war
EXPOSE 8080
