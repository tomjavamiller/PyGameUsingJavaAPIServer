#
#FROM openjdk:8-jdk-alpine
FROM openjdk:11
LABEL maintainer="tom.com"
MAINTAINER tom.com
ADD build/libs/mastermind-0.0.1-SNAPSHOT.jar mastermind.jar
ENTRYPOINT ["java","-jar","/mastermind.jar"]
