# SpringAuthStarter

SpringAuthStarter is a **plug-and-play authentication starter kit** for Spring Boot applications. It provides ready-to-use **JWT Authentication**, **Google & GitHub OAuth2 login**, and **role-based access control**, so you can integrate secure authentication into any backend project in minutes.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-success)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


---

## **Features**

* **JWT-based Authentication** – Secure stateless APIs.
* **Google & GitHub OAuth2 Login** – Easy third-party login integration.
* **Role-Based Access Control** – Built-in roles: `ADMIN`, `USER`.
* **Rate Limiting** - Limit user login requests per minute using JBucket.
* **Default Admin & User Creation** – Auto-create accounts via `.env` or environment variables.
* **Swagger-Friendly OAuth URLs** – Copy-paste OAuth login URLs directly from API docs.
* **Easily Reusable** – Copy the auth package into any Spring Boot app.

---

## **Getting Started**

### **1. Clone the repository**

```bash
git clone https://github.com/yourusername/SpringAuthStarter.git
cd SpringAuthStarter
```

### **2. Configure Environment Variables**

SpringAuthStarter supports `.env` files in local development (via `spring-dotenv`) and standard environment variables in production.

**Create a `.env` file in the project root:**

```env
SPRING_APPLICATION_NAME=SpringAuthStarter

DB_HOST=localhost
DB_PORT=3306
DB_NAME=db_name
DB_USER=root
DB_PASSWORD=your_password

JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQLDialect
JPA_HIBERNATE_DDL_AUTO=update
JPA_SHOW_SQL=true

JWT_SECRET=secret_key
JWT_EXPIRATION=3600000

GOOGLE_CLIENT_ID=your_client_id
GOOGLE_CLIENT_SECRET=your_client_secret
GOOGLE_SCOPE=profile,email

GITHUB_CLIENT_ID=your_client_id
GITHUB_CLIENT_SECRET=your_client_secret
GITHUB_SCOPE=user:email
GITHUB_CLIENT_NAME=GitHub
GITHUB_AUTH_URI=https://github.com/login/oauth/authorize
GITHUB_TOKEN_URI=https://github.com/login/oauth/access_token
GITHUB_USER_INFO_URI=https://api.github.com/user
GITHUB_USER_NAME_ATTRIBUTE=login

DEFAULT_ADMIN_EMAIL=admin@example.com
DEFAULT_ADMIN_PASSWORD=Admin@123
DEFAULT_USER_EMAIL=user@example.com
DEFAULT_USER_PASSWORD=User@123
```

> **Note:** Never commit `.env` to version control.

---

### **3. Run the application**

#### From source:

```bash
mvn spring-boot:run
```

*(Requires Spring Boot Maven plugin in `pom.xml`)*

#### From JAR:

```bash
mvn clean package
java -jar target/SpringAuthStarter-0.0.1-SNAPSHOT.jar
```

---

## **Default Account Auto-Creation**

On startup, the application will:

* Check if `DEFAULT_ADMIN_EMAIL` and `DEFAULT_ADMIN_PASSWORD` exist.
* If the account doesn’t exist in DB, create it with role `ADMIN`.
* Do the same for `DEFAULT_USER_EMAIL` and `DEFAULT_USER_PASSWORD` with role `USER`.

This is handled by `DataInitializer` using Spring's `Environment` abstraction so it works with `.env` and production env vars.

---

## **OAuth Login Flow**

### **Direct login URLs**

Instead of redirecting inside Swagger, the login endpoints return the OAuth login URL as JSON.

Example:

```json
{
  "loginUrl": "http://localhost:8080/oauth2/authorization/google"
}
```

Paste the URL into a new browser tab to start the OAuth login flow.

---

## **Integrating Into Your Own App**

1. Copy the `security`, `jwt`, and `oauth` packages into your project.
2. Update your database config and OAuth credentials in `.env` or `application.properties`.
3. Include the dependencies from this starter in your `pom.xml`.

---

## **Why Use SpringAuthStarter?**

* Saves days of boilerplate setup.
* Works with both **MySQL** and **PostgreSQL**.
* OAuth-ready out of the box.
* Easy to extend for custom roles and permissions.

---

## **License**

This project is licensed under the MIT License.
