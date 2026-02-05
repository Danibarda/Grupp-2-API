# Grupp 2 API

Ett Rest API byggt med Jakarta EE tillsammans med quarkus för att hantera bilar samt användare. Detta API innehåller full CRUD-funktionalitet samt kräver autentisiering via API-nycklar.

## Funktionalitet:

***Bilhantering:***
- Visa alla bilar
- Visa bilar utifrån ditt personliga ID
- Lägg till nya bilar
- Uppdatera ägda bilars miltal
- Radera bilar från din ägo

***Användarhantering:***
- Skapa din användare
- Hämta dina uppgifter via API-nyckel

  ## API-endpoints:

  ***Bilhantering***
  - ***/api/car*** - Visa alla bilar
  - ***/api/car/{id}*** - Visa bilar utifrån personligt ID
  - ***/api/car*** - Lägg till ny bil (API-nyckel krävs)
  - ***/api/car/{id}***	- Uppdatera miltal (API-nyckel krävs)
  - ***/api/car/{id}***	- Radera vald bil (API-nyckel krävs)
 
  ***Användare***
  ***/api/users*** - Skapa ny användare
  ***/api/users/me*** - Hämta dina uppgifter (API-nyckel krävs)

## Kör projektet
***Klona repo:t***
- git clone<repo-url>
- cd Grupp-2-API

 ***Installera dependencies***
 - ./mvnw clean install

***Konfigurera databasen i "src/main/resources/application.properties":***

***Starta utvecklingsmiljö***
- ./mvnw quarkus:dev

- API tillgängligt på:: ***http://localhost:8080/api/***
- Swagger UI tillgängligt på: ***http://localhost:8080/swagger-ui***
## Relaterade guider

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- Narayana JTA - Transaction manager ([guide](https://quarkus.io/guides/transaction)): JTA transaction support
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC


