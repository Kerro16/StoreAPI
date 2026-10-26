# StoreAPI

## Description

This project is a Spring Boot API for an online store. It provides essential functionalities for managing products, orders, and customers in an e-commerce environment.


## Authentication

This API utilizes JSON Web Tokens (JWT) for authentication, which are set as cookies.

### Obtaining a Token

To obtain a JWT token, you need to send a valid username and password to the `/api/auth/signin` endpoint. The server will respond by setting a cookie with the JWT token if the credentials are valid.

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



## Configuration

Secrets are **not** stored in the repository. Copy `.env.example`, fill in the values and export them as
environment variables before starting the API:

| Variable | Purpose |
|---|---|
| `DB_PASSWORD` | PostgreSQL password (required) |
| `JWT_SECRET` | Base64 string of 32+ random bytes, e.g. `openssl rand -base64 48` (required) |
| `DB_URL`, `DB_USERNAME` | Database connection (defaults to local PostgreSQL) |
| `ALLOWED_ORIGINS` | Comma-separated CORS origins (default `http://localhost:4200`) |
| `COOKIE_SECURE` | Set to `true` behind HTTPS |

## Access rules

- `POST /api/auth/signup` always creates a regular user (`ROLE_USER`); the requested role is ignored.
- `GET /api/product/**` is public; creating, updating or deleting products needs `ADMIN` or `MODERATOR`.
- `/api/users/**` needs `ADMIN` and never returns password hashes.
