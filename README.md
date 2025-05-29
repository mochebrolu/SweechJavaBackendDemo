# Spring MVC + MyBatis + JWT Backend

This is a secure backend API built with Java 8, Spring MVC, MyBatis, and MySQL, using Docker for containerization. It supports user registration, login, posts, comments, login tracking, and weekly login rankings.

---

## 🚀 Features

- ✅ User Registration and Login with JWT Authentication
- 🔐 Bearer Token Security (except for `/signup` and `/login`)
- 📝 Post Management (Create, List, Detail)
- 💬 Comment System with Cursor-based Pagination
- 📊 Weekly Login Ranking
- 📦 Dockerized Setup (App + MySQL)

---

## 📦 Tech Stack

- Java 8
- Spring MVC
- MyBatis
- Maven
- MySQL
- Apache Tomcat (Spring Boot embedded)
- JWT (Token-based Auth)
- Docker / Docker Compose

---

## ⚙️ Setup

### 1. Clone the Repository

```bash
git clone https://github.com/mochebrolu/SweechJavaBackendDemo.git
cd your-repo

2. Build the Project
bash
Copy
Edit
mvn clean package
3. Run with Docker Compose
bash
Copy
Edit
docker-compose up --build
The backend will be available at:
👉 http://localhost:3001

📄 API Endpoints
🔐 Auth
POST /api/signup

POST /api/login

👤 User
PATCH /api/user – Update password or username (Bearer token required)

📚 Posts
POST /api/posts

GET /api/posts?page=1

GET /api/posts/{id}

💬 Comments
POST /api/comments/{postId}

GET /api/comments/{postId}?cursor=ID

DELETE /api/comments/{commentId}

📈 Login & Ranking
GET /api/login-history

GET /api/rankings/weekly

🧪 Testing
Basic unit tests are included using JUnit. You can run them with:

bash
Copy
Edit
mvn test
🧰 Tools Used
Spring Boot
MyBatis
jwt
Docker Compose
