FROM openjdk:17
ARG FILE_JAR=target/*.jar

ADD ${FILE_JAR} api-shop-app.jar

ENTRYPOINT ["java", "-jar", "api-shop-app.jar"]

EXPOSE 8080