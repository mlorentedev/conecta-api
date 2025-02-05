FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/conecta-0.0.1.jar conecta-0.0.1.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "conecta-0.0.1.jar"]
