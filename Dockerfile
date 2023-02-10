# Build stage
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package
FROM openjdk:latest
VOLUME /dasdi-capstone-be
COPY --from=build /home/app/target/dasdi-football-service.jar dasdi-football-service
ENTRYPOINT ["java","-jar","/dasdi-football-service"]
EXPOSE 8080