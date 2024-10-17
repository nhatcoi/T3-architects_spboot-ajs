Project Documentation: ShopApp
Overview
ShopApp is a Spring Boot-based application designed to manage an online shop. It includes functionalities for managing products, categories, orders, users, and roles.

Technologies Used:
    Java 17
    Spring Boot 3.3.3
    Maven
    PostgreSQL
    HikariCP
    Spring Data JPA
    Spring Security
    JWT (JSON Web Token)
    Lombok
    ModelMapper
    Data Faker

Project Structure:
    src/main/java/org/example/shopapp: Contains the main application code.
    src/main/resources: Contains configuration files like application.yml.
    pom.xml: Maven configuration file.

Configuration:
    DATABASE_URL=
    DATABASE_USER=
    DATABASE_PASSWORD=


Running the Application:
    1. Set up the database: Ensure PostgreSQL is running and the database is created.
    2. Configure environment variables: Create a .env file with the necessary database credentials.
    3. Build and run the application:
        mvn clean install
        mvn spring-boot:run

Usage
    API Endpoints: The API endpoints are prefixed with /api/v1.
    Authentication: Uses JWT for securing endpoints.

Additional Notes:
    Logging: Configure logging levels in application.yml.