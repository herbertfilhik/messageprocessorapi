# Message Processor API
Welcome to the Message Processor API project! This repository contains an API for message processing using Spring Boot and Spring Kafka.

## Overview
This project provides a solution for processing messages received from a Kafka topic and performing specific operations based on the received messages. It is a Spring Boot application configured to interact with Kafka and execute message processing.

## Prerequisites
Before getting started, make sure you have the following requirements installed:
- Java JDK 8 or higher
- Apache Kafka (if running locally)
- Maven (optional, if you prefer to build the project with Maven)

## Configuration
1. Clone this repository:
git clone https://github.com/your-username/messageprocessorapi.git

## Configure Dependency to External Project:**
Use project dependence `route-generator`, follow the stepd:
- Navegue até a pasta `libs` no repositório clonado.
- In the Folder `libs` into cloned repository.
- Search for JAR file `route-generator-1.0-SNAPSHOT.jar`.
- Add the dependence in the file `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.example</groupId>
       <artifactId>route-generator</artifactId>
       <version>1.0-SNAPSHOT</version>
       <scope>system</scope>
       <systemPath>${project.basedir}/libs/route-generator-1.0-SNAPSHOT.jar</systemPath>
   </dependency>


## Other Kafka properties
- spring.kafka.bootstrap-servers=your-kafka-broker:9092
- spring.kafka.consumer.group-id=consumer-group

## Build and run the project:
cd messageprocessorapi
./mvnw spring-boot:run

## Usage
The API can be accessed at http://localhost:8080. Be sure to check the API documentation for details on available endpoints.

- curl --location 'http://localhost:8080/routes/completed'

- curl --location 'http://localhost:8080/routes/{idEvent}/events'

- curl --location 'http://localhost:8080/routes/{idEvent}/force-complete'

## Swagger
OpenAPI definition
http://localhost:8080/swagger-ui/index.html

## Contributing
Feel free to contribute to this project by opening issues and pull requests. All contributions are welcome!

## License
This project is licensed under the MIT License - see the LICENSE file for details.
