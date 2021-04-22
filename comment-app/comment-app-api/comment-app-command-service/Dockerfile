#FROM adoptopenjdk/openjdk8:alpine-slim
FROM openjdk:8-jdk-alpine
RUN mkdir /opt/app

EXPOSE 8080

COPY target/comment-app-command-service-0.0.1-SNAPSHOT.jar /opt/app

CMD java ${JAVA_OPTIONS} -jar /opt/app/comment-app-command-service-0.0.1-SNAPSHOT.jar