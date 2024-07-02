FROM openjdk:17-oracle
ARG JAR_FILE=*.jar
ARG STARTER_FILE=*.json
COPY ${JAR_FILE} application.jar
COPY ${STARTER_FILE} starter.json
ENTRYPOINT ["java", "-jar", "application.jar"]