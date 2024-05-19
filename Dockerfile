FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar UniversityWebApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/UniversityWebApp-0.0.1-SNAPSHOT.war"]