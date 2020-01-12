FROM openjdk:8-jdk-alpine
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
RUN apk update && apk add bash
COPY api-login-1.0.0.jar api-login-1.0.0.jar
ENTRYPOINT ["./wait-for-it.sh","docker-mysql:3306", "--","java","-jar","api-login-1.0.0.jar"]

