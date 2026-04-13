FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only the project folder
COPY job-portall ./job-portall

WORKDIR /app/job-portall

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=build /app/job-portall/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
