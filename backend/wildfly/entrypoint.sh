#!/bin/bash
set -e

# Inicia o Wildfly em background
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 &
WILDFLY_PID=$!

# Aguarda o management subir (porta 9990 é a padrão do management)
until curl -s http://localhost:9990/management/health > /dev/null; do
  echo "Aguardando Wildfly subir..."
  sleep 2
done

# Executa o script CLI para configurar o H2
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --file=/opt/jboss/wildfly/configure-h2.cli

# (Opcional) Mensagem de sucesso
echo "Configuração H2 aplicada com sucesso."

# Mantém o Wildfly rodando em foreground
wait $WILDFLY_PID