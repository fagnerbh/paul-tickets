FROM openjdk:11

EXPOSE 8064

COPY target/PaulTickets-1.3.jar /usr/src

CMD ["java", "-jar", "/usr/src/PaulTickets-1.3.jar"]