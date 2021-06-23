FROM openjdk:11
ADD target/customers-0.0.1-SNAPSHOT.jar /usr/share/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/share/app.jar"]
