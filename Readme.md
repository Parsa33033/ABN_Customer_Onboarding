# Customer Onboarding API

table of contents:
- [Overview](#overview)
- [Design](#design)
- [Domain Entities](#domain-entities)
- [Tests](#tests)
- [How to run](#how-to-run)
- [Use of AI and AutoComplete](#use-of-ai-and-autocomplete)
- [What this API lacks](#what-this-api-lacks)

## Overview

This project is a Customer Onboarding API built using Java and Spring Boot. It follows the Hexagonal 
Architecture pattern and Domain-Driven Design (DDD) principles to ensure a clean and maintainable codebase. 

## Design

Design Decision Records (DDR):

The requirements for this project were:
1) simplicity
2) readability
3) maintainability
4) extensibility
5) use of best practices

To meet these requirements, the following design decisions were made:
- **Use of Hexagonal Architecture**: This architecture pattern was 
  chosen to 
  separate the core business logic from the infrastructure and external 
  systems. This makes the codebase more maintainable and extensible.
- **Use of Entity Domain Objects (from DDD)**: This approach was chosen to 
  encapsulate the business logic and rules within the domain objects, making 
  the code more readable and maintainable. Also validation goes through 
  these domain objects making the service layer thinner.
- **Concurrency Handling**: The application is designed to handle concurrent 
  requests safely by using stateless services and thread-safe components 
  using CompletableFuture

#### Layers:


- [Application Layer](./src/main/java/nl/abc/onboarding/customer/application/CustomerApplication.java)
  
    This layer does the following:
    - generate a new UUID identifier for the customer
    - save the photo and return the path
    - save the id document and return the path
    - call the domain customer onboarding service operation with the new 
      identifier, photo path, and id document path


- [Domain Layer](./src/main/java/nl/abc/onboarding/customer/domain/services/CustomerServiceImpl.java)

  This layer does the following:
    - validate the customer data according to business rules through Domain 
      Entity object creation (CustomerEntity)
    - read the customer by external id from the repository
    - if the customer already exists, return the customer
    - if the customer does not exist, create a new customer entity


- [Infrastructure Layer](./src/main/java/nl/abc/onboarding/customer/infrastructure/repositories/CustomerInteractionRepositoryImpl.java)

  This layer does the following:
    - read the customer data from the database by external id
    - save the customer data to the database
  

#### Architecture Diagram:


![Architecture diagram](./design/sequence-model-onboard-customer.png)


## Domain Entities

Below are the domain entity fields and types as implemented in the codebase. Value objects (e.g., Email, PhoneNumber, Gender) are validated wrappers around primitive values; their DTO mapping uses primitives.

### CustomerEntity

Source: [CustomerEntity.java](./src/main/java/nl/abc/onboarding/customer/domain/ports/entities/CustomerEntity.java)

| Field | Type | Notes |
|------|------|-------|
| identifier | UUID | Unique internal identifier |
| externalIdentifier | String | External system identifier |
| firstName | String |  |
| lastName | String |  |
| gender | Gender | Value object; maps to String in DTO |
| dateOfBirth | LocalDate |  |
| phoneNumber | PhoneNumber | Value object; maps to String in DTO |
| email | Email | Value object; maps to String in DTO |
| nationality | String |  |
| residentialAddress | AddressEntity | Maps to AddressData in DTO |
| socialSecurityNumber | String |  |
| idDocumentPath | String | File path where ID document is stored |
| photoPath | String | File path where profile photo is stored |

DTO mapping: [CustomerData.java](./src/main/java/nl/abc/onboarding/customer/domain/ports/dtos/CustomerData.java)

### AddressEntity

Source: [AddressEntity.java](./src/main/java/nl/abc/onboarding/customer/domain/ports/entities/AddressEntity.java)

| Field | Type |
|------|------|
| address | String |
| zipCode | String |
| city | String |
| country | String |

DTO mapping: [AddressData.java](./src/main/java/nl/abc/onboarding/customer/domain/ports/dtos/AddressData.java)

Note: AddressData uses a record component named "Country" (capital C) by design; the entity field is "country".

## Tests

- Application layer (integration): [CustomerApplicationIT.java](./src/test/java/nl/abc/onboarding/customer/application/CustomerApplicationIT.java)
  - Hits the /onboarding endpoint end-to-end with multipart form data, verifies 200 response, generated identifier/instructions, and that uploaded files are created. Also checks invalid email returns 400 with a clear message.
- Domain layer (unit): [CustomerServiceImpTest.java](./src/test/java/nl/abc/onboarding/customer/domain/services/CustomerServiceImpTest.java)
  - Covers onboarding when customer exists vs. created, domain validation failures (throws DomainEntityException), and propagation of repository read/write errors.
- Infrastructure layer (data/JPA): [CustomerInteractionRepositoryTest.java](./src/test/java/nl/abc/onboarding/customer/infrastructure/repositories/CustomerInteractionRepositoryTest.java)
  - Persists and retrieves Customer by externalIdentifier, including residential Address mapping.

## How to run

1. build the project using maven:
   ```bash
   mvn clean install
   ```
   
2. run the spring boot application:
   ```bash
   mvn spring-boot:run
   ```

3. call the api using one of the methods below.

#### Swagger UI

http://localhost:8083/swagger-ui.html


#### Using Postman

- import the provided Postman collection file 
  [Customer Onboarding API.postman_collection.json](./collection/CustomerOnboarding.postman_collection.json)
  into your Postman application.

4. to check out the data in the database, you can use the H2 console:

#### H2Database Console
For simplicity, this project uses an in-memory H2 database to store customer data.
However, by changing the settings in the application.properties file, you can easily switch
to another database like MySQL or PostgreSQL.

note: to see the h2 database console while running the API application,
go to http://localhost:8083/h2-console and enter the following details:

 - JDBC URL: jdbc:h2:mem:onboarding
 - username: sa
 - password: (leave blank)


## Use of AI and AutoComplete

this project used gpt to help with:

- autocompletion of lines (mostly comments, javadocs, attribute names, and 
  strings)
- attribute generation for classed (dto, entity, getters/setters)
- setting up application properties
- generation of this very readme file
- creation of file storage (methods that have been created by AI has javadoc 
  that mentions it was created by AI)
- Generation of swagger yaml file

** Basically all labour non-technical work has been done by AI. **


## What this API lacks

- removing the photo from the file storage when the customer is not created 
   due to technical errors.
- integration test for when the customer already exists. (not needed for demo
   purposes)
- using docker and docker-compose with a bash file to start the whole 
  application using ./start.sh:

Docker was not necessary for this project. the point was simplicity.

But if it is needed to run the application in a docker container:
- download docker
- run docker
- build image for your required database
- run the container with the spring application and assign the database
  container volume to the application container
- adjust the application.properties file to point to the database container

