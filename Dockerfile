FROM jboss/wildfly

# add deployment archive.
ADD ./build/libs/commons-langC-doc-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

# add an admin user.
#RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent

# run as standalone mode.
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]