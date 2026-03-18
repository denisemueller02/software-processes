# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .

# Give execute permission to gradlew
RUN chmod +x ./gradlew

# Build the JAR
RUN ./gradlew clean build -x checkstyleMain -x checkstyleTest

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
