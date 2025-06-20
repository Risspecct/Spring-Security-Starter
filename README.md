# 🔐 SpringAuthStarter

A production-ready Spring Boot starter for JWT-based authentication and role-based authorization.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![License](https://img.shields.io/badge/License-Apache_2.0-green)

---

## 📖 Overview

This is a plug-and-play Spring Boot project that sets up robust JWT-based authentication and authorization. It includes secure login, registration, role-based access (USER/ADMIN), and a clean architecture for scalability and learning.

---

## ✨ Features

* ✅ Stateless JWT authentication with role-based authorization
* ✅ Spring Security 6 with custom `UserDetailsService` and `AuthenticationProvider`
* ✅ Admin-only endpoints with user management
* ✅ BCrypt password hashing (strength: 12)
* ✅ Global exception handling with detailed error responses
* ✅ Input validation using Jakarta Bean Validation
* ✅ DTO-to-Entity mapping via MapStruct
* ✅ Clean and modular code structure

---

## 📃 Use Cases

* Secure backend APIs for web/mobile apps
* Microservice auth layers
* MVPs and hackathons
* Backend learning projects for students

---

## ⚙️ Setup Instructions

```bash
git clone https://github.com/yourname/SpringAuthStarter.git
cd SpringAuthStarter
```

1. Update `src/main/resources/application.properties`:

    * Configure MySQL DB URL, username/password
    * Set JWT secret key and expiration

2. Run the application:

```bash
./mvnw spring-boot:run
```

3. Test APIs via Postman, Swagger UI, or cURL

---

## 🔢 API Overview

| Method | Endpoint            | Auth Required | Role  | Description                 |
| ------ | ------------------- | ------------- | ----- | --------------------------- |
| POST   | `/register`         | No            | -     | Register new user           |
| POST   | `/login`            | No            | -     | Login and get JWT           |
| POST   | `/logout`           | Yes           | USER  | Dummy logout (client-based) |
| GET    | `/user/`            | Yes           | USER  | Get current user info       |
| DELETE | `/user/`            | Yes           | USER  | Delete own account          |
| GET    | `/admin/users`      | Yes           | ADMIN | Get list of all users       |
| DELETE | `/admin/users/{id}` | Yes           | ADMIN | Delete user by ID           |

> Note: The `logout` endpoint is implemented as a placeholder. Frontend should delete the token from local/session storage.

---

## 📂 Project Structure

```
src/main/java
└─ users.rishik.SpringAuthStarter
    ├─ Controllers/          # Auth, User, Admin controllers
    ├─ Dtos/                 # DTOs for request payloads
    ├─ Entities/             # JPA entities and enums
    ├─ Exceptions/           # Global error handling
    ├─ jwt/                  # JWT filter, service, and config
    ├─ Repositories/         # Spring Data interfaces
    ├─ Security/             # Security config and user principal
    ├─ Services/             # Business logic and authentication
    └─ UtilityClasses/       # View interfaces and mappers
```

---

## 🔐 Security Design

* Stateless JWT auth using `Authorization: Bearer <token>` header
* Role-based access with method-level security via `@PreAuthorize`
* `JwtAuthenticationFilter` for validating JWT on each request
* Passwords stored using BCrypt hashing

---

## 📊 Tech Stack

* Java 21
* Spring Boot 3.5
* Spring Security 6
* Spring Data JPA
* MySQL
* JWT (io.jsonwebtoken)
* MapStruct
* Lombok

---

## 🔧 Configuration Sample

```properties
# Application
spring.application.name=SpringAuthStarter

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=your_secret_key
jwt.expiration=3600000  # 1 hour

# Exception Handling
spring.mvc.throw-exception-if-no-handler-found=true
spring.web.resources.add-mappings=false
```

---

## 💼 License

Licensed under the **Apache License 2.0**. You are free to use and modify this project. **Attribution is appreciated but not required.**

If you find this project helpful and use it in your own work, a mention or link to this repo would be greatly appreciated!

---

## 🚀 Contributing

Found a bug or want to suggest a feature? Contributions are welcome via PRs or issues.
