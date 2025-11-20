# Customer Onboarding API

## Design

![Architecture diagram](./design/sequence-model-onboard-customer.png)


## How to run

note: to see the h2 database console while running the API application,
go to http://localhost:8080/h2-console


## Use of AI and AutoComplete

this project used chatGTP to help with:

- autocompletion of lines (especially comments and strings)
- attribute generation for classed (dto, entity, getters/setters)
- setting up application properties
- generation of this very readme file
- generation code duplication: AddressEntity was created by AI based on an 
  already created CustomerEntity architecture.

** Basically all labour work has been copied from AI. **



