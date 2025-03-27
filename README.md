# Calculator API – Build & Run Instructions
This RESTful API provides the basic functionalities of a calculator. Communication between modules was supposed to be only by Kafka (see disclaimer)

##  Requirements

To build and run this project, you only need:

- Only Docker (https://www.docker.com/) - everything runs on Docker

## Build and Run the Project

From the root of the project directory, run:
docker-compose up --build

## Disclaimer: Is not fully working, since it the calculator cannot connect to the kafka streams (unknown issue, do not have time to fix), although everything runs on docker and both modules can connect to Kafka and docker
