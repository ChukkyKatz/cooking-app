FROM openjdk:17
COPY target/cooking-app.jar /app/cooking-app.jar
RUN mkdir -p /app/logs
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/cooking-app.jar"]