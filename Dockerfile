FROM openjdk:17

LABEL Tizian="tizianappler@yahoo.de"

ADD backend/target/MyBudget.jar MyBudget.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -jar /MyBudget.jar" ]