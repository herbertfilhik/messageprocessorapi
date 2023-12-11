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

- curl  --location 'http://localhost:8080/routes/force-complete/' \
		--header 'Content-Type: application/json' \
		--data '    {
		        "id": "386d13b0-4d38-49c5-8fe8-c1bda9002820",
		        "courierId": "c177598e-686c-4c0b-8fa3-578a4cd67ef9",
		        "status": "COMPLETED",
		        "eventTime": 1702192648,
		        "originId": "8598dc07-7040-4b42-ae6d-949036f8c71a",
		        "destinationId": "36605a83-e96d-477f-a63e-74c846dad7a3"
		    }'
		    
- curl --location 
	   --request POST 'http://localhost:8080/routes/9e561803-4ac4-4eb1-9106-100633a9bf33/force-complete'		    		    

## Swagger
OpenAPI definition
http://localhost:8080/swagger-ui/index.html

## Contributing
Feel free to contribute to this project by opening issues and pull requests. All contributions are welcome!

## License
This project is licensed under the MIT License - see the LICENSE file for details.
