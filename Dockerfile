FROM openjdk:17

MAINTAINER Tizian tizianappler@yahoo.de

ADD target/MyBudget.jar MyBudget.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -jar /MyBudget.jar" ]