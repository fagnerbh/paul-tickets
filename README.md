# paul-tickets
concurrent requisition test to a limited resource queue.

create jar
mvn package -DskipTests

docker network create event-network

create database
docker run -p 3307:3306 --name percona-event --network event-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=event -d percona:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker build:
docker build -t event:0.0.1 .

docker run
docker run -p 8064:8064 --network event-network -d --name event-container event:0.0.1

to run k6 docker script: docker run --rm -i grafana/k6 run - <k6.js
