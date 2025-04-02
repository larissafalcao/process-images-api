# Process Images API
This project is an API to do process images.

### Requires ###
* Java 21
* Docker - https://docs.docker.com/install/
* Docker compose - https://docs.docker.com/compose/install/
* Maven - https://maven.apache.org/download.cgi
* RabbitMQ - https://www.rabbitmq.com/download.html

## Execute docker-compose for local environment
The docker-compose file its located at the project root.
```sh
docker-compose up -d
```
This command it's going to start:
- Postgres server.
- RabbitMQ server.

### Start application server from code by maven ###
```sh
mvn spring-boot:run
```

After the application started access the url:
```sh
http://localhost:8080/swagger-ui/index.html#
```
You should see the swagger documentation

### Tests ###
Tests are run automatically when the application is built.
