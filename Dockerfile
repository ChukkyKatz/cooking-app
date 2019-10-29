FROM openjdk:12
COPY target/cooking-app.jar /app/cooking-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/cooking-app.jar"]