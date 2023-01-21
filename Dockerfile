FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/superhero.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]