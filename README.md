## 🔐 SpringAuthStarter

A **reusable and extensible Spring Boot starter project** designed for developers who need a robust, production-ready implementation of **JWT-based authentication and authorization** using Spring Security. Ideal for anyone looking to **kickstart secure REST API development** with clean architecture, proper exception handling, and modern development practices.

---

### 🛠 Features

* ✅ JWT-based authentication and stateless authorization
* ✅ Spring Security 6 with custom `UserDetailsService` and `AuthenticationProvider`
* ✅ Pre-built user registration and login APIs
* ✅ Passwords hashed using BCrypt
* ✅ Token validation via filter (`JwtAuthenticationFilter`)
* ✅ Global error handling with custom `@RestControllerAdvice`
* ✅ DTO validation with Jakarta Bean Validation
* ✅ MapStruct integration for cleaner DTO-to-Entity mapping
* ✅ MySQL + Spring Data JPA setup for persistence
* ✅ Easily customizable and modular codebase

---

### 📦 Tech Stack

* Java 21
* Spring Boot 3.5
* Spring Security 6
* Spring Data JPA
* MySQL
* JWT (`io.jsonwebtoken`)
* MapStruct
* Lombok

---

### 📁 Project Structure

```
src/main/java
└── users.rishik.SpringAuthStarter
    ├── Exceptions/             # Centralized exception handling
    ├── jwt/                    # JWT configuration, filter, service
    ├── Security/               # Spring Security setup
    ├── user/                   # User model, controller, service, repository
    └── SpringAuthStarterApplication.java
```

---

### 🗓️ REST API Endpoints

| Method | Endpoint         | Description                         | Auth Required |
| ------ | ---------------- | ----------------------------------- | ------------- |
| POST   | `/user/register` | Register a new user                 | No            |
| POST   | `/user/login`    | Login and retrieve JWT token        | No            |
| GET    | `/user/`         | Retrieve authenticated user details | Yes           |
| DELETE | `/user/`         | Delete current authenticated user   | Yes           |

---

### ⚙️ Configuration (application.properties)

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
jwt.expiration=3600000  # in milliseconds (e.g., 1 hour)

# Error Handling
spring.mvc.throw-exception-if-no-handler-found=true
spring.web.resources.add-mappings=false
```

---

### 🎓 Getting Started

1. **Clone the repository**
2. **Update** your `application.properties`
3. **Build and run** the application using your IDE or `./mvnw spring-boot:run`
4. **Test** the endpoints using Postman, cURL, or Swagger (if added)

---

### 🌟 Why Use This?

This template is ideal for:

* Quickly bootstrapping secure Spring Boot applications
* Developers learning Spring Security + JWT
* Teams needing a clean and extendable base for microservices
* Demonstrating backend skills with secure auth flows in portfolios

---

### 🚫 License

This project is licensed under the **Apache License 2.0**. Feel free to use and extend it in your own applications.

---

### 🔗 Contributions

Have improvements or suggestions? Open an issue or submit a PR!
