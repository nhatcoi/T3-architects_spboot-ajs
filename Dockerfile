FROM openjdk:17

ARG JAR_FILE=/target/shopapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} shopapp.jar
ENTRYPOINT ["java", "-jar", "shopapp.jar"]
EXPOSE 8080