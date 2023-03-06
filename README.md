
# Mintyn-Task Producer Documentation

&nbsp;

### Language :

	Java 11

### Framework :

	Springboot 2.7.4

### Database :

    Mysql Databse

&nbsp;


### Apache Kafka Set-Up:

make sure you have Apache Kafka running, if you don't please follow the link below to get Apache Kafka running locally

https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273


### How to run :

#### Option 1 (Run in docker container)

If you have docker installed, bring up your terminal, navigate to the root directory of the project  and follow the steps below

* To build the code run the command below--

  docker build -t mintynproducer .

* To run the code run the command below--

  docker run -it -p 1800:81 mintynproducer

#### Option 2 (Run from IDE)

open the project in Intellij-IDEA and run or follow the guide in the link below for better explanation
https://www.youtube.com/watch?v=kQ6Zkb6s6mM

&nbsp;

### Link to Postman collection

https://we.tl/t-Om9f7IAh6j

### Link to Swagger documentation
http://localhost:1800/swagger-ui.html#/


### Things I would have done better if I had more time

1. I would add an index to the order table as this would reduce the time spent in retrieving data.
2. I would write more test for the service class and improve test coverage of the controller class.
3. I would run a load test using Jmeter and improve the performance of the code depending on the Jmeter report.