FROM eclipse-temurin:17
ARG JAR_FILE=brt-app-service/target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/application.jar"]
