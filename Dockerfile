FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

MAINTAINER Prashant Gupta

COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/letslearn-graphql-server-0.0.1-SNAPSHOT.jar /app/
COPY src/main/resources/schema/* /app/src/main/resources/schema/

ENTRYPOINT ["java", "-jar", "letslearn-graphql-server-0.0.1-SNAPSHOT.jar"]