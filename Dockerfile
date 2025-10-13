FROM gradle:8.14.3-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/franchises-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]