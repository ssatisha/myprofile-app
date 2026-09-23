# Stage 1: Build the Application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project definition and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the executable JAR
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Runtime Image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy compiled JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 and run the app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]