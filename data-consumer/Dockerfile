FROM java:8
COPY ./env/application.properties /usr/data/consumer/
COPY ./target/consumer-0.0.1-SNAPSHOT.jar /usr/data/consumer/
WORKDIR /usr/data/consumer
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "consumer-0.0.1-SNAPSHOT.jar"]