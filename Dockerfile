FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

# ✅ Move into correct folder
WORKDIR /app/job-portall

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17

WORKDIR /app

# ✅ Copy built jar from correct location
COPY --from=build /app/job-portall/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
