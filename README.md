# StoreAPI

## Description

This project is a Spring Boot API for an online store. It provides essential functionalities for managing products, orders, and customers in an e-commerce environment.


## Authentication

This API utilizes JSON Web Tokens (JWT) for authentication, which are set as cookies.

### Obtaining a Token

To obtain a JWT token, you need to send a valid username and password to the `/api/auth/singin` endpoint. The server will respond by setting a cookie with the JWT token if the credentials are valid.

## Technologies Used
- Java 
- SpringBoot
- PostgreSQL
- FrontEnd: [FrontStore](https://github.com/Kerro16/FrontStore)
- Lombok
- JPA Hibernate
  

- **POST /api/auth/sigin**
  - Description: Obtain a JWT token.
  - Request:
    ```json
    {
      "username": "your_username",
      "password": "your_password"
    }
    ```
  - Response: A cookie named `store_cookie` is set with the JWT token.

### Using the Token

Once you have obtained a token, include it in the headers of your requests. The token is automatically sent as a cookie with the name `jwt_token` in subsequent requests.

### Refreshing the Token

Tokens have a limited lifespan. If your token expires, you can obtain a new one using the refresh token.

### Logging Out

To log out, you can simply clear the `jwt_token` cookie.

```bash
curl -X POST http://localhost:8080/api/auth/logout

