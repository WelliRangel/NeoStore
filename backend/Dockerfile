# Etapa 1: Build do WAR com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Deploy do WAR no WildFly
FROM quay.io/wildfly/wildfly:35.0.1.Final-jdk21

# Copia o WAR gerado para o diretório de deploy do WildFly
COPY --from=build /app/target/neostore.war /opt/wildfly/standalone/deployments/neostore.war

# Configuração do datasource (opcional: pode ser feito via CLI ou standalone.xml)
# Exemplo: copiar um arquivo de configuração customizado, se necessário
# COPY standalone.xml /opt/wildfly/standalone/configuration/standalone.xml

# Exponha a porta padrão do WildFly
EXPOSE 8080

# Inicia o WildFly, ouvindo em todas as interfaces
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]