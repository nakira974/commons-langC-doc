FROM jboss/wildfly

# Arguments pour les chemins du certificat et de la clé
ARG ssl_cert
ARG ssl_key

RUN ${JBOSS_HOME}/bin/jboss-cli.sh --commands="embed-server,\
   /subsystem=undertow/server=default-server/https-listener=https:add(socket-binding=https, security-realm=ssl-realm),\
   /subsystem=security/security-realm=ssl-realm:add(),\
   /subsystem=security/security-realm=ssl-realm/authentication=truststore:add(keystore-path=${ssl_cert}, keystore-password=password, keystore-relative-to=jboss.server.config.dir),\
   /subsystem=security/security-realm=ssl-realm/authentication=keystore:add(keystore-path=${ssl_key}, keystore-password=password, keystore-relative-to=jboss.server.config.dir)"


# add deployment archive.
ADD ./build/libs/commons-langC-doc-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

# add an admin user.
#RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent

# run as standalone mode.
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]