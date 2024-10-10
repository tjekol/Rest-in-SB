FROM gradle:8-jdk21 AS builder

# working directory on container
WORKDIR /home/gradle

# copy necessary files and folders
COPY settings.gradle.kts gradlew backend/build.gradle.kts ./

COPY backend/src src
COPY backend/build build
COPY gradle gradle

# run the application
RUN gradle bootJar

# rename the jar file
RUN mv build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:21-alpine

# use new user to instead of root
RUN addgroup -g 1000 app
RUN adduser -G app -D -u 1000 -h /app app

USER app
WORKDIR /app

# copy the app from the builder image
COPY --from=builder --chown=1000:1000 /home/gradle/app.jar .

CMD ["java", "-jar", "app.jar"]
