# Stage 1: Build the JAR file
FROM gradle:8.8.0-alpine AS build
RUN addgroup -S ethfetchergroup && adduser -S ethfetchereuser -G ethfetchergroup
WORKDIR /app
RUN chown -R ethfetchereuser:ethfetchergroup /app
USER ethfetchereuser
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test --no-daemon

# Stage 2: Create the runtime image
FROM amazoncorretto:21-alpine3.20
RUN addgroup -S ethfetchergroup && adduser -S ethfetchereuser -G ethfetchergroup
WORKDIR /app
RUN chown -R ethfetchereuser:ethfetchergroup /app
USER ethfetchereuser
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
