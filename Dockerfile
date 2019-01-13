FROM openjdk:8-jdk
COPY build/libs/city-parking-v0.0.1.jar city-parking.jar

EXPOSE 8080

ENV DATABASE_CONNECTION_STRING "jdbc:postgresql://127.0.0.1:5432/city_parking"
ENV DB_USER city_parking
ENV DB_PASSWORD city_parking

CMD java -jar city-parking.jar