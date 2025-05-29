FROM openjdk:8
VOLUME /tmp
ADD target/backend.jar app.jar
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "app.jar"]
