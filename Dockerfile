FROM openjdk:17-alpine
EXPOSE 8081
ADD target/user-app.jar user-app.jar
ENTRYPOINT ["java","-jar","/user-app.jar"]