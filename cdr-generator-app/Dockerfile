FROM eclipse-temurin:17
ARG JAR_FILE=cdr-generator-app-service/target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]
