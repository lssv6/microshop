FROM eclipse-temurin:22-alpine AS base

COPY ../../ms-backend/app/build/libs/app.jar /opt

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
