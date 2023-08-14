FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Pawel"
ENV DB_URL=r2dbc:postgresql://postgres:postgres@127.0.0.1:5432/autofleet
VOLUME /tmp
COPY build/libs/*.jar ./app/
ENTRYPOINT ["java", "-jar", "./app/autofleet-0.0.2-SNAPSHOT.jar"]