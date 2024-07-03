# hub-products-management

This Spring Boot application provides a RESTful API for managing products, offering functionalities like listing, adding, updating products, and basic authentication with role-based access control.

## Feature
 - Get a paginated list of products.
 - Get product by id
 - Add new product
 - Update product
 - Basic Authentication with Role based access control
 - Exception handler
 - Logging

## Requirements
 - Java 17
 - Maven

## Setup
1. Clone Repository 
    ```sh
    git clone https://github.com/danielbradea/hub-products-management.git
    cd hub-products-management
    ```
2. Build the project
    ```sh
    mvn clean install
   ```
3. Run the app
    ```sh
   mvn spring-boot:run
   ```

## Accessing Swagger API Documentation
Once the application is running, you can access the Swagger UI to view and interact with the API documentation:
- Open a web browser and go to: `http://localhost:8080/swagger-ui/index.html`

### Authentication

#### Admin Role
- **Username**: admin
- **Password**: admin

#### User Role
- **Username**: user
- **Password**: user