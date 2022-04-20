FROM openjdk:11

COPY target/PaulTickets-0.0.1-SNAPSHOT.jar /usr/src

CMD ["java", "-jar", "/usr/src/PaulTickets-0.0.1-SNAPSHOT.jar"]