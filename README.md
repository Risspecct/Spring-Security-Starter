# SpringAuthStarter – Secure Your Spring Boot APIs in Minutes

**SpringAuthStarter** is a production-ready authentication starter kit for Spring Boot. It offers **JWT-based authentication**, **Google & GitHub OAuth2 login**, and **role-based access control** — all in one reusable package to secure your backend quickly.

**Live Demo:** [https://spring-security-starter.onrender.com](https://spring-security-starter.onrender.com)

![Java](https://img.shields.io/badge/Java-21-blue) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)![Database](https://img.shields.io/badge/Database-PostgreSQL%20%7C%20MySQL-orange) ![Security](https://img.shields.io/badge/Security-JWT%20%7C%20OAuth2-green) ![Docker](https://img.shields.io/badge/Container-Docker-blue) ![Hosting](https://img.shields.io/badge/Hosting-Render-purple)

---

## 📑 Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Architecture](#architecture)
4. [Tech Stack](#tech-stack)
5. [Installation & Setup](#installation--setup)
6. [Usage](#usage)
7. [Environment Variables](#environment-variables)
8. [API Documentation](#api-documentation)
9. [Why Use This](#why-use-this)
10. [Contributing](#contributing)
11. [License](#license)

---

## Overview

SpringAuthStarter eliminates the need to write repetitive Spring Security boilerplate. It’s ideal for:

* Securing REST APIs with JWT.
* Adding social login via Google & GitHub.
* Enforcing role-based endpoint protection.
* Providing ready-to-use Swagger API docs.

---

## Features

**Authentication:**

* JWT issuance & validation for stateless auth.
* Password hashing with BCrypt.

**Authorization:**

* Role-based access control (`ADMIN`, `USER`).
* Secure admin-only and user-only routes.

**OAuth2:**

* Google & GitHub login.
* Direct OAuth login URLs in JSON responses.

**Utilities:**

* Rate limiting via Bucket4J.
* Default admin/user creation from environment variables.
* Swagger UI for interactive API testing.

---

## Architecture

1. User sends login/registration request.
2. Backend validates credentials & issues JWT.
3. JWT is used for all subsequent protected requests.
4. OAuth2 flow handled by Spring Security client.

---

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.5
* **Security:** Spring Security, OAuth2 Client
* **Database:** MySQL/PostgreSQL
* **Others:** JJWT, Bucket4J, Spring Data JPA, Swagger
* **Containerization:** Docker
* **Hosting:** Render

---

## Installation & Setup

```bash
git clone https://github.com/yourusername/SpringAuthStarter.git
cd SpringAuthStarter
```

Create `.env` (see `.env.example`) with DB, JWT, and OAuth credentials.

Run locally:

```bash
mvn spring-boot:run
```

Or build & run JAR:

```bash
mvn clean package
java -jar target/spring-auth-starter.jar
```

---

## Usage

### Register User

```bash
POST /register
{
  "email": "user@example.com",
  "username": "User",
  "password": "pass123",
  "role": "USER"
}
```

### Login & Get JWT

```bash
POST /login
{
  "email": "user@example.com",
  "password": "pass123"
}
```

Use the returned JWT as `Authorization: Bearer <token>` for protected routes.


### Default Users for Testing

To help you explore the features of this app, you can use the following test accounts:

| Role  | Email                                         | Password  |
| ----- | --------------------------------------------- |-----------|
| Admin | [admin@example.com](mailto:admin@example.com) | Admin@123 |
| User  | [user@example.com](mailto:user@example.com)   | User@123  |

> **Note:** These accounts are for testing purposes only. You can also register your own account if you prefer.

**How to Test:**

1. Visit the app login page: [https://spring-security-starter.onrender.com](https://spring-security-starter.onrender.com)
2. Use one of the above credentials to log in.
3. Explore features according to your role (Admin or User).

---

## Environment Variables

| Variable              | Description            | Example                                       |
| --------------------- | ---------------------- | --------------------------------------------- |
| DB\_HOST              | Database host          | localhost                                     |
| DB\_PORT              | Database port          | 5432                                          |
| JWT\_SECRET           | Secret key for JWT     | change\_me                                    |
| GOOGLE\_CLIENT\_ID    | Google OAuth client ID | abc.apps.googleusercontent.com                |
| DEFAULT\_ADMIN\_EMAIL | Admin account email    | [admin@example.com](mailto:admin@example.com) |

---
## API Documentation

Swagger UI is available at: `/swagger-ui.html` or `/swagger-ui/index.html` or `/docs`.

---

## Why Use This

* Saves days of setup work.
* Works out-of-the-box with OAuth2.
* Easily extendable for custom roles/permissions.
* Production-ready security configuration.

---

## Contributing

Pull requests are welcome! For major changes, open an issue first to discuss.

---

## License

MIT License – see [LICENSE](LICENSE) file for details.
