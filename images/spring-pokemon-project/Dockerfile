FROM openjdk:8
MAINTAINER Antoine Hunkeler & Florian Polier
# from here : https://spring.io/guides/gs/spring-boot-docker/
# and here : https://hub.docker.com/_/openjdk
COPY ./sources/ /usr/src/myapp
WORKDIR /usr/src/myapp
RUN mvn spring-boot:run