FROM openjdk:8-jdk-alpine


ARG FLAFILES=flatfiles/census.csv
COPY ${FLAFILES} census.csv

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

RUN chmod 777 census.csv

ENTRYPOINT ["java","-jar","/app.jar"]