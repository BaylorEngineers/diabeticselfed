FROM maven:3.8.4-openjdk-17-slim AS build
COPY src /home/diabeticselfed/src
COPY contents /home/diabeticselfed/contents
COPY pom.xml /home/diabeticselfed
#RUN mvn -f /home/diabeticselfed/pom.xml clean package
RUN mvn -f /home/diabeticselfed/pom.xml clean package -DskipTests

FROM openjdk:17-slim

COPY --from=build /home/diabeticselfed/contents /diabeticselfed/contents
COPY --from=build /home/diabeticselfed/target/*.jar diabeticselfed.jar


EXPOSE 8080
ENTRYPOINT ["java","-jar","/diabeticselfed.jar"]