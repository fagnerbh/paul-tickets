# paul-tickets
concurrent requests test to a limited resource queue. Simulates 
concurrent requests for tickets for events in numbered seat venues.

create jar
mvn package -DskipTests

docker network create event-network

create database
docker run -p 3307:3306 --name paul-tickets_percona-event_1 --network event-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=event -d percona:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker build:
docker build -t event:0.0.1 .

docker run
docker run -p 8064:8064 --network event-network -d --name event-container event:0.0.1

to push docker image to docker hub
docker build -t <hub-user>/<repo-name>[:<tag>] .
docker push <hub-user>/<repo-name>:<tag>

to access the kubenetes service by calling its rest endpoint:
check it out the VM/docker IP in its terminal

Kubernetes
------------

create secret to docker hub image
kubectl create secret docker-registry eventcred --docker-server=https://index.docker.io/v2/ --docker-username=<docker-username> --docker-password=<your-pword> --docker-email=<email>

to set minikube to Oracle VM:
minikube start --driver=virtualbox
minikube config set driver virtualbox


to run k6 docker script:
docker run --rm -i grafana/k6 run - <k6.js

to run prometheus:
docker run -p 9090:9090 -v config/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

to run grafana:
docker run -p 3000:3000 grafana/granfana:latest
