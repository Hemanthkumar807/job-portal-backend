FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy only required files first (better build)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Now copy source
COPY src src

# Build project
RUN ./mvnw clean package -DskipTests

# Run jar
CMD ["java", "-jar", "target/job-portall-0.0.1-SNAPSHOT.jar"]