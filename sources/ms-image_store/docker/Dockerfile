FROM eclipse-temurin:22-alpine AS base

COPY sources/ms-image_store/app/build/libs/app.jar /opt/
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
