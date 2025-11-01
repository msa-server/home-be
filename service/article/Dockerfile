# build stage
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /repo
COPY . .
RUN chmod +x gradlew
RUN ./gradlew :service:article:bootJar --no-daemon

# runtime stage
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /repo/service/article/build/libs/*.jar app.jar

EXPOSE 9000
ENTRYPOINT [ "sh", "-c", "java -jar /app/app.jar" ]