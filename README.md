# 🔐 SpringAuthStarter

A fully-featured Spring Boot authentication and authorization starter template with JWT, refresh token support, role-based access control, and production-grade features. Built for learning, rapid prototyping, and real-world backend systems.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
![License](https://img.shields.io/badge/License-Apache_2.0-green)

---

## 📖 Overview

SpringAuthStarter provides a plug-and-play authentication service with stateless JWT-based access, persistent refresh tokens, method-level security, rate limiting, and clean architecture. It is ideal for both personal projects and real-world backend setups.

---

## ✨ Features

* 🔑 JWT-based authentication (access + refresh tokens)
* 🔐 OAuth 2.0 login with GitHub and Google using Spring Security's client support 
* 🔄 Refresh token flow with token expiry and persistence
* 🧑‍⚖️ Role-based access control (USER / ADMIN) with `@PreAuthorize`
* 🧾 Structured global error handling with timestamped JSON responses
* 🔒 BCrypt password hashing (strength: 12)
* 📉 Rate limiting (Bucket4j) on authentication endpoints
* 🧼 DTO ↔ Entity conversion with MapStruct
* 🧪 Extensive Postman collection (positive + negative flows)
* 📚 Swagger/OpenAPI documentation
* 🛡 Secure headers (XSS, content sniffing, frame blocking)
* 🧱 Modular, scalable code structure

---

## 📂 Project Structure

```
src/main/java
└── users.rishik.SpringAuthStarter
    ├── admin/              # AdminController and AdminService
    ├── config/             # Security and Role Hierarchy Configurations
    ├── exceptions/         # Custom exceptions and global handler
    ├── jwt/                # JWT auth (controllers, DTOs, service, filter, config)
    ├── oauth/              # OAuth services, success handler, and user principal
    ├── rate_limiter/       # IP-based request limiting logic
    ├── user/               # Core user logic, roles, security, repository
    ├── util/               # Home controller, view models, mappers
    └── SpringAuthStarterApplication.java
```

---

## ⚙️ Getting Started

```bash
git clone https://github.com/yourname/SpringAuthStarter.git
cd SpringAuthStarter
./mvnw spring-boot:run
```

1. Update DB and JWT settings in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your_secret_key
jwt.expiration=3600000
```

2. Launch Swagger UI at `http://localhost:8080/swagger-ui.html`

3. Or import the provided Postman collection for complete testing.

---

## 🔐 Authentication Flow

1. **User registers or logs in** → receives access and refresh tokens
2. **Access token** used to access protected endpoints
3. **When access token expires** → use `/refreshToken` with refresh token
4. **New access token** is issued without re-authentication

Tokens are stored client-side (e.g., localStorage) and sent via `Authorization: Bearer <token>`

---

## 🔢 API Endpoints Summary

| Method | Endpoint            | Auth | Role  | Description              |
| ------ | ------------------- | ---- | ----- | ------------------------ |
| POST   | `/register`         | ❌    | -     | Register new user        |
| POST   | `/login`            | ❌    | -     | Login, returns tokens    |
| POST   | `/refreshToken`     | ❌    | -     | Get new access token     |
| POST   | `/logout`           | ✅    | USER  | Dummy client-side logout |
| GET    | `/user/`            | ✅    | USER  | Get current user info    |
| DELETE | `/user/`            | ✅    | USER  | Delete own account       |
| GET    | `/admin/users`      | ✅    | ADMIN | List all users           |
| DELETE | `/admin/users/{id}` | ✅    | ADMIN | Delete user by ID        |

---

## 🧪 Validation & Error Handling

### ✅ Common Responses

```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "email: must be a well-formed email address",
  "path": "/register",
  "timestamp": "2025-06-21T15:18:48.257Z"
}
```

### ❌ Negative Case Coverage

* Duplicate email
* Invalid credentials
* Invalid input format (bad enum, missing fields)
* Unauthorized (401) and forbidden (403) access

---

## 🧰 Postman Suite

Included Postman collection features:

* Register, login, logout
* Refresh token testing
* Admin and user-protected endpoints
* Auto-token extraction using scripts
* Negative scenarios for robustness testing

---

## 📦 Tech Stack

* Java 21
* Spring Boot 3.5
* Spring Security 6
* Spring Data JPA + MySQL
* JWT (JJWT 0.12.6)
* OAuth 2.0
* MapStruct for mapping
* Lombok
* Bucket4j for rate limiting
* Swagger (SpringDoc 2.x)

---

## 💼 License

Licensed under the Apache License 2.0. Attribution is optional but appreciated.

---

## 🤝 Contributing

Pull requests, suggestions, and issues are welcome!

> Built by Rishik for serious backend development and learning.
