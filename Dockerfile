# STAGE 1 : BUILD
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY . .
RUN ["mvn", "clean", "test", "package", "verify"]

# STAGE 2 : APP
FROM eclipse-temurin:21.0.6_7-jre-alpine-3.21 AS final
WORKDIR /opt/app
EXPOSE 8080
COPY --from=build /build/target/.jar /opt/app/.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]