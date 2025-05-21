FROM eclipse-temurin:21-alpine
WORKDIR /opt/backend
COPY ./app/build/libs/app.jar .
ENTRYPOINT ["java", "-jar", "./app.jar"]
