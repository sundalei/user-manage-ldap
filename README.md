# Spring Boot LDAP User Management Demo

This project is a demonstration of how to build a Spring Boot application that performs user management operations against an OpenLDAP server. It showcases CRUD (Create, Read, Update, Delete) functionalities for users stored in an LDAP directory.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 17 or later
- Apache Maven
- Docker and Docker Compose

## 1. Setup OpenLDAP Server and Populate Data

This project uses Docker Compose to quickly set up an OpenLDAP server.

### Steps

**Start the LDAP Containers:**

Open a terminal in the root directory of the project and run:

```bash
docker compose -f manifests/compose.yaml -f manifests/compose.local.yaml up -d
```

This command will download the necessary images (if not already present) and start the `openldap-server` container in detached mode.

- OpenLDAP will be accessible on `ldap://localhost:1389`.

**Verify Data (Optional):**

You can verify that the data has been loaded using `ldapsearch`.

- **Using ldapsearch:**

```bash
docker exec -it openldap-server ldapsearch -x -H ldap://localhost:1389 -b "dc=springframework,dc=org" -D "cn=admin,dc=springframework,dc=org" -w password "(uid=dbrown)"
````
This command searches for the user with `uid=dbrown`.

## 2. Running the Spring Boot Application

Once the LDAP server is running and populated, you can start the Spring Boot application.

```bash
mvn spring-boot:run
```

The application will connect to the OpenLDAP server as configured in `src/main/resources/application.yml`.
API endpoints will be available at `http://localhost:8080`.

## 3. Running Tests

The project includes integration tests that use an embedded LDAP server populated with data from `src/test/resources/test-users.ldif`.

To run the tests:

```bash
mvn test
```

This command will execute the JUnit tests, which verify the functionality of the LDAP repository.

## 4. Understanding LDAP Concepts

A brief overview of LDAP terms used in this project:

- **LDAP (Lightweight Directory Access Protocol):** A protocol for accessing and maintaining distributed directory information services.
- **Directory:** A specialized database optimized for reading, browsing, and searching. It stores information in a hierarchical, tree-like structure.
- **DN (Distinguished Name):** A unique identifier for an entry in the LDAP directory. It represents the path from the root of the directory to the entry.
  - Example: `uid=dbrown,ou=groups,dc=springframework,dc=org`
- **RDN (Relative Distinguished Name):** The part of the DN that is unique at its level in the hierarchy.
  - Example: For the DN above, `uid=dbrown` is an RDN.
- **Entry:** A collection of attributes with a unique DN. Represents an object (e.g., a person, an organizational unit).
- **Attribute:** A piece of information associated with an entry (e.g., `cn`, `sn`, `uid`, `userPassword`). Each attribute has a type and one or more values.
- **ObjectClass:** Defines the type of an entry and specifies which attributes are allowed and required. Common objectClasses for users include `person`, `organizationalPerson`, and `inetOrgPerson`. `top` is a base objectClass.
- **OU (Organizational Unit):** An entry that can contain other entries, used to group related objects.
  - Example: `ou=groups` in this project.
- **DC (Domain Component):** Used to represent parts of a DNS domain name.
  - Example: `dc=springframework,dc=org` corresponds to `springframework.org`.
- **LDIF (LDAP Data Interchange Format):** A plain text format for representing LDAP entries and update operations. Used in `users.ldif` and `test-users.ldif`.

## 5. Source Code Overview

The project follows a standard Spring Boot application structure:

- **`com.example.SpringLdapApplication.java`**: The main Spring Boot application class.

- **`com.example.config`**:

  - `LdapNameSerializer.java`: A Jackson serializer for `javax.naming.ldap.LdapName` objects, ensuring DNS are represented as strings in JSON responses.

- **`com.example.controller`**:

  - `UserController.java`: A REST controller that exposes HTTP endpoints for user management (e.g., `/users`, `/users/{uid}`). It handles incoming requests, interacts with the `UserService`, and returns responses. DTOs are used for request and response payloads.

- **`com.example.dto`**:

  - `UserDTO.java`: Data Transfer Object used to carry user data between the controller and clients. This helps decouple the API representation from the internal entity structure. **Note:** In a production system, password fields should not be included in DTOs sent to the client.

- **`com.example.entity`**:

  - `User.java`: An LDAP entity class mapped to user entries in the directory. It uses Spring LDAP ODM (Object Directory Mapping) annotations (`@Entry`, `@Id`, `@Attribute`, `@DnAttribute`) to define how Java objects correspond to LDAP entries and their attributes.
    - `@Entry(base = "ou=groups", objectClasses = { ... })\`: Specifies the base OU for these entries and their LDAP object classes.

- **`com.example.exception`**:

  - `GlobalExceptionHandler.java`: Uses `@RestControllerAdvice` to provide centralized exception handling for the application, converting specific LDAP exceptions and others into appropriate HTTP responses.

- **`com.example.repository`**:

  - `UserRepository.java`: A Spring Data LDAP repository interface extending `LdapRepository`. It provides standard CRUD methods and allows defining custom query methods (e.g., `findByUid`, `findBySn`).
  - `UserRepositoryCustom.java`: An interface for custom repository methods that require more complex LDAP queries.
  - `impl/UserRepositoryImpl.java`: Implementation of `UserRepositoryCustom`, using `LdapTemplate` to construct and execute custom LDAP queries.

- **`com.example.service`**:
  - `UserService.java`: Contains the business logic for user management. It acts as an intermediary between the `UserController` and the `UserRepository`, orchestrating operations like creating, finding, updating, and deleting users.

### Key Configuration Files

- **`pom.xml`**: The Maven Project Object Model file. It defines project dependencies (Spring Boot starters for Web and LDAP, testing libraries), build configurations, and project metadata.
- **`src/main/resources/application.yml`**: The main application configuration file.
  - `spring.application.name`: Sets the application name.
  - `spring.ldap.*`: Configures the connection to the primary OpenLDAP server (URL, base DN, admin username, and password).
- **`src/test/resources/application.yml`**: Configuration specific to tests.
  - `spring.ldap.embedded.*`: Configures an embedded LDAP server for integration tests, including the LDIF file (`test-users.ldif`) to load, the base DN, and port.
- **`compose.yaml`**: Docker Compose file defining the `openldap` and `phpldapadmin` services, their images, ports, environment variables, and volumes.
- **`users.ldif`**: Contains sample data for the development LDAP server.
- **`src/test/resources/test-users.ldif`**: Contains sample data for the embedded LDAP server used during tests.

## API Endpoints

The `UserController` exposes the following RESTful endpoints:

- `POST /users`: Creates a new user.
- `GET /users`: Retrieves all users.
- `GET /users/{uid}`: Retrieves a user by their UID.
- `GET /users/surname/{surname}`: Finds users by surname.
- `GET /users/cn/{commonName}/sn/{surname}`: Finds users by common name and surname.
- `PUT /users/{uid}`: Updates an existing user.
- `DELETE /users/{uid}`: Deletes a user by UID.

Refer to `UserController.java` and `UserDTO.java` for request/response body structures.
**Security Note:** As implemented, this demo transmits and potentially exposes passwords in plain text. In a real-world application, passwords must be hashed before storage, and never exposed in API responses.
