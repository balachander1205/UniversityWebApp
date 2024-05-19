FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvm clean install

FROM openjdk:17-jdk-slim
COPY --from=build /target/UniversityWebApp-0.0.1-SNAPSHOT.war UniversityWebApp.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","UniversityWebApp.war"]