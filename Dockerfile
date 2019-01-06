FROM openjdk:10.0.1-10-jre
ARG artifactVersion

ENV workdir /opt/zssn

WORKDIR $workdir
COPY target/zssn-${artifactVersion}.jar $workdir/zssn.jar
COPY target/classes/application.properties $workdir/config/application.properties

EXPOSE 8080

ENTRYPOINT java $JAVA_OPTS --add-modules java.se.ee -jar zssn.jar
