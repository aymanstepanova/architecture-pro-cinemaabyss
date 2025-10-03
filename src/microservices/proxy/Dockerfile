FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
ENV PORT=8000
COPY --from=build /app/target/proxy-service-*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/app/app.jar"]



#FROM maven:3.9-eclipse-temurin-17 AS build
#WORKDIR /src
#COPY pom.xml .
#COPY src ./src
#RUN mvn -q -DskipTests clean package
#
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#COPY --from=build /src/target/proxy-service-*.jar app.jar
#EXPOSE 8000
#ENTRYPOINT ["java","-jar","/app/app.jar"]

