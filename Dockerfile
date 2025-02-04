FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/conecta-api-0.0.1.jar conecta-api-0.0.1.jar

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "conecta-api-0.0.1.jar"]
