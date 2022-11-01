# Solution for homework from Trixi  

## How to start
- Git clone
- Create DB schema `trixi` in MySQL DB
- Change DB credentials in `application.properties` file if necessary  
- Start Spring application

## App workflow
- Upon start up, 2 tables will be created in `trixi` DB schema - `towns` (Obec) and `districts` (CastiObce)
- Zipped XML file will be downloaded into `src` folder
- Processing of the XML file and inserting data into DB

## Tech stack
- Java Spring Boot
- JPA
- MySQL
- StAX
