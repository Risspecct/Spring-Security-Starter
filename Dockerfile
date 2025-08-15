# Stage 1 - Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the JAR (skip tests for speed)
RUN mvn clean package -DskipTests

# Stage 2 - Run
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the specific JAR from target
COPY --from=build /app/target/spring-auth-starter.jar spring-auth-starter.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-auth-starter.jar"]
