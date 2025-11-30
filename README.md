# MindEase Backend

**MindEase** is the backend service powering a web-based mental health and productivity platform designed specifically for students.  
It provides secure APIs for stress management, time scheduling, AI chatbot support, and sleep improvement tools — all optimized for seamless integration with the frontend (React.js).

---

## Overview

The backend of **MindEase** is built with **Java Spring Boot**, designed for scalability, modularity, and performance.  
It handles the business logic, authentication, API management, AI communication, and database operations.

### Core Responsibilities
- Manage user authentication and authorization.
- Handle scheduling and reminder features (including email notifications).
- Store and retrieve user progress, sleep tracking, and activity logs.
- Integrate with external APIs (YouTube for meditation videos, AI model for chatbot).
- Ensure secure data storage and reliable communication with the frontend.

---

##  Architecture

```text
Frontend (e.g React.js)
       ↓ (REST API)
Backend (Spring Boot)
       ↓
Database (PostgreSQL)
       ↓
External APIs (AI, YouTube)
```

### Components

| Component | Technology | Description |
|------------|-------------|--------------|
| **Framework** | Spring Boot | Core web framework for backend services. |
| **ORM** | JPA / Hibernate | Object-relational mapping and database access. |
| **Database** | PostgreSQL | Securely stores user data, progress, and chatbot history. |
| **Authentication** | Spring Security + JWT | Manages user sessions and authorization. |
| **Containerization** | Docker | Ensures consistency across development and deployment environments. |
| **AI Integration** | OpenAI / GPT API (via backend) | Powers empathetic chatbot interactions. |
| **Email Service** | JavaMail / SMTP | Sends task reminders and updates to users. |

---

## Authentication & Security

- **JWT-based Authentication** for stateless session management.  
- **Password Encryption** using BCrypt for user credentials.  
- **CORS Configuration** to safely connect frontend (React) and backend (Spring Boot).  
- **Role-based Access Control (RBAC)** for different user permissions if needed in future updates.

---

## API Endpoints (Examples)

| Endpoint                   | Method | Description |
|----------------------------|--------|-------------|
| `/api/auth/register-user`  | `POST` | Registers a new user. |
| `/api/auth/login`          | `POST` | Authenticates and returns JWT token. |
| `/api/tasks`               | `GET` | Fetches all user tasks. |
| `/api/tasks`               | `POST` | Adds a new scheduled task. |
| `/api/sleep`               | `GET` | Retrieves sleep tracking data. |
| `/api/sleep`               | `POST` | Logs sleep session information. |
| `/api/chat`                | `POST` | Sends user input to AI model and returns chatbot response. |
| `/api/notifications/email` | `POST` | Sends reminder emails. |

> ⚙️ All endpoints return structured JSON responses and use proper HTTP status codes.

---

## AI Chatbot Integration

- The backend connects to an **LLM API (e.g., GPT)** to generate context-aware and empathetic responses.
- Each session maintains minimal contextual history to allow personalized guidance.
- Messages and summaries can be stored securely in PostgreSQL for analysis and progress tracking.

---

## Database Schema (Simplified)

| Table | Purpose |
|--------|----------|
| `users` | Stores user profile, email, and encrypted password. |
| `tasks` | Stores user tasks and reminder info. |
| `sleep_logs` | Records sleep sessions, duration, and quality notes. |
| `chat_history` | Logs chatbot-user conversations for progress insights. |
| `email_reminders` | Tracks sent reminders and schedule times. |

---

## Setup & Installation

### 1. Clone the Repository

## How to Run

---
1. Clone the repository
   ```bash
   git clone https://github.com/MuhammedBakor/mind-ease-backend.git


2. Make sure PostgreSQL is running and update application.yaml with your DB credentials.

3. Run the app

    ```bash
    mvn clean install
    mvn spring-boot:run

    docker-compose up --build

### 4. Test endpoints via Postman.

# Development Tools
### Java 21+
### Spring Boot 3.x
### PostgreSQL
### Maven
### Docker
### IntelliJ IDEA / VS Code

___

# Contributors

## Backend Team (Mohammad Bakur Ibrahim, Augustine Alul)– responsible for:

- API design & documentation
- Authentication & security
- AI integration
- Database schema & optimization
- Email & notification services



