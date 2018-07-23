FROM java:8
COPY ./env/application.properties /usr/data/producer/
COPY ./target/producer-0.0.1-SNAPSHOT.jar /usr/data/producer/
WORKDIR /usr/data/producer/
ENTRYPOINT ["java", "-jar", "producer-0.0.1-SNAPSHOT.jar"]