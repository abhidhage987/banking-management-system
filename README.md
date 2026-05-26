# 🏦 Banking Management System

A secure and scalable Banking Management System developed using Java, Spring Boot, Spring Security, JWT Authentication, PostgreSQL, Redis, Docker, and Swagger/OpenAPI.

This project demonstrates real-world backend development concepts including authentication, authorization, RBAC, transaction management, REST APIs, Docker containerization, Redis caching, and enterprise-level architecture.

---

# 🚀 Features Implemented

✅ User Registration & Login  
✅ JWT Authentication  
✅ Spring Security Integration  
✅ Role-Based Access Control (RBAC)  
✅ Create Bank Account  
✅ Deposit Money  
✅ Withdraw Money  
✅ Transfer Money  
✅ Transaction History  
✅ Admin APIs  
✅ PostgreSQL Database Integration  
✅ Redis Cache Integration  
✅ Docker Support  
✅ Swagger/OpenAPI Documentation  
✅ Exception Handling  
✅ Validation  
✅ REST APIs  

---

# 🛠️ Technologies Used

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Redis
- Docker
- Docker Compose
- Swagger/OpenAPI
- Hibernate/JPA
- Maven

---

# 📂 Project Architecture


Client
   ↓
REST APIs
   ↓
Spring Security
   ↓
JWT Authentication
   ↓
Controllers
   ↓
Service Layer
   ↓
JPA/Hibernate
   ↓
PostgreSQL

+ Redis Cache
+ Docker
+ Swagger


🔐 Authentication

Authentication is used to verify user identity using email and password.

Implemented Using:
Spring Security
JWT Authentication
BCrypt Password Encoder
APIs:
POST /api/auth/register
POST /api/auth/login
Flow:
User Login
    ↓
Password Verification
    ↓
JWT Token Generated
🛡️ Authorization

Authorization controls which APIs a user can access after login.

Implemented Using:
Spring Security Role Authorization
Example:
.requestMatchers("/api/admin/**")
.hasRole("ADMIN")
Result:
ADMIN can access admin APIs
CUSTOMER can access account APIs
👥 RBAC (Role-Based Access Control)

Different roles have different permissions.

Roles:
ADMIN
CUSTOMER
Implemented Using:
UserRole Enum
Spring Security Roles
Example:
.hasAnyRole("CUSTOMER", "ADMIN")
🔑 JWT Authentication

JWT (JSON Web Token) is used for stateless authentication.

Implemented Using:
JwtUtil
JwtAuthenticationFilter
SecurityConfig
Flow:
Login
   ↓
JWT Generated
   ↓
Token Sent in Headers
   ↓
User Authenticated
🗂️ JPA Relationships

Relationship between User and Account entities.

Implemented Using:
@ManyToOne
@JoinColumn(name = "user_id")
Meaning:

One user can have multiple bank accounts.

💸 Transaction Management

Safe money transfer implementation.

Features:
Deposit
Withdraw
Transfer Money
Transaction History
Flow:
Deduct Balance
      ↓
Add Balance
      ↓
Save Transaction
Benefit:

Prevents partial money transfer failures.

⚡ Redis Cache

Redis is used as in-memory caching database.

Implemented Using:
Redis Docker Container
Spring Redis Configuration
Purpose:
Fast data access
Future caching support
Session optimization
🐳 Docker Containerization

Application is fully containerized using Docker.

Implemented Using:
Dockerfile
docker-compose.yml
Containers:
Spring Boot App
PostgreSQL
Redis
Docker Commands Used:
docker compose build
docker compose up
docker compose down
📄 Swagger / OpenAPI

Swagger is used for API documentation and testing.

URL:
http://localhost:8080/swagger-ui/index.html
Benefits:
API Testing
API Documentation
Frontend Integration Support
🐘 PostgreSQL Database

Relational database used to store project data.

Tables:
bank_users
accounts
transactions
Implemented Using:
Spring Data JPA
Hibernate
🌐 REST APIs

REST APIs are used for communication between frontend and backend.

Controllers:
AuthController
AccountController
AdminController
HTTP Methods Used:
GET
POST
DELETE
⚠️ Exception Handling

Custom exceptions and proper error handling implemented.

Examples:
ResourceNotFoundException
Invalid Password Exception
Insufficient Balance Exception
Benefit:

Provides meaningful error responses instead of application crash.

📌 API Endpoints
Authentication APIs

Register User
POST /api/auth/register

Login User
POST /api/auth/login

Account APIs
Create Account
POST /api/account/create

Deposit Money
POST /api/account/deposit

Withdraw Money

POST /api/account/withdraw

Check Balance
GET /api/account/balance/{accountNumber}

Transfer Money
POST /api/account/transfer

Transaction History
GET /api/account/transactions/{accountNumber}

Admin APIs
Get All Users
GET /api/admin/users

Get All Accounts
GET /api/admin/accounts

Delete Account
DELETE /api/admin/account/{id}

▶️ How to Run the Project
1️⃣ Clone Repository
git clone YOUR_GITHUB_REPOSITORY_URL
2️⃣ Open Project

Open project in:

Spring Tool Suite (STS)
IntelliJ IDEA
Eclipse
3️⃣ Configure PostgreSQL

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/banking_db
spring.datasource.username=postgres
spring.datasource.password=postgres
4️⃣ Run Redis Using Docker
docker run --name redis-bank -p 6379:6379 -d redis
5️⃣ Run Application
mvn spring-boot:run

OR

Run directly from STS Boot Dashboard.

6️⃣ Open Swagger
http://localhost:8080/swagger-ui/index.html

🔥 Future Enhancements
Refresh Token System
Email Notifications
Kafka Integration
RabbitMQ
Microservices Architecture
API Gateway
React Frontend
CI/CD Pipeline
Kubernetes Deployment


👨‍💻 Author

Abhishek Dhage

Backend Developer | Java & Spring Boot Enthusiast
