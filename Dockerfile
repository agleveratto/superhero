FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/superhero.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]