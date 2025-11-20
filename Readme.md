# Customer Onboarding API

## Design

![Architecture diagram](./design/sequence-model-onboard-customer.png)


## How to run

note: to see the h2 database console while running the API application,
go to http://localhost:8080/h2-console


## Use of AI and AutoComplete

this project used gpt to help with:

- autocompletion of lines (mostly comments, javadocs, attribute names, and 
  strings)
- attribute generation for classed (dto, entity, getters/setters)
- setting up application properties
- generation of this very readme file
- creation of file storage (methods that have been created by AI has javadoc 
  that mentions it was created by AI)

** Basically all labour non-technical work has been done by AI. **


## What this API lacks

- removing the photo from the file storage when the customer is not created 
   due to technical errors.
- intergration test for when the customer already exists. (not needed for demo )
   purposes)


