FROM wellirangel/neostorewildfly:latest

# Copia o arquivo neostore.war para o diretório de deploy do WildFly
COPY target/neostore.war /opt/jboss/wildfly/standalone/deployments/

# Inicia o WildFly com as configurações especificadas
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c", "standalone.xml"]
