# paul-tickets
concurrent requisition test to a limited resource queue.

create jar
mvn package -DskipTests

docker network create event-network

create database
docker run -p 3307:3306 --name paul-tickets_percona-event_1 --network event-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=event -d percona:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker build:
docker build -t event:0.0.1 .

docker run
docker run -p 8064:8064 --network event-network -d --name event-container event:0.0.1

to access the service by calling its rest endpoint:
check it out the VM/docker IP in its terminal
