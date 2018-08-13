# Server app
Server application for monitoring data about users. [Client application](https://github.com/avtsuran/client-app/)
## Prerequisites
You will need the following things properly installed on your computer:

* [Maven](https://maven.apache.org/)
* [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [PostgeSQL](https://www.postgresql.org/)
* [ActiveMQ](http://activemq.apache.org/)

Before using app you must create database on PostgreSQL server. To create db use next commands:
````
$ CREATE DATABASE phone_book;
````
````
$ \c phone_book;
````
````
$ CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    phone VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL
  );
````

Before running app you should configure file src/main/resources/application.properties

## Run app
To run app use next command:
````
$ mvn spring-boot:run
````