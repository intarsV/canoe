FROM openjdk:8
ADD canoe-0.0.1-SNAPSHOT.jar canoe-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "canoe-0.0.1-SNAPSHOT.jar"]