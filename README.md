# 🔐 Spring Boot JWT Authentication Project

This project demonstrates how to implement **JWT (JSON Web Token) based authentication** and **authorization** using **Spring Security 6** and **Spring Boot 3**.  
It provides secure access control for REST APIs without using session storage.

---

## 🚀 Features
- User registration and login with JWT token generation
- Token validation using `OncePerRequestFilter`
- Stateless authentication (no HTTP session)
- Role-based access control
- Custom `UserDetailsService` implementation
- Secure endpoints with `AuthenticationProvider`
- Clean project structure for scalability

---

## 🧩 Tech Stack
- **Spring Boot 3**
- **Spring Security 6**
- **JWT (io.jsonwebtoken)**
- **Spring Data JPA**
- **MySQL / H2 Database**
- **Maven**
- **Java 17+**

---

## 📂 Project Structure

src/
├── main/
│ ├── java/com/spring/security/
│ │ ├── controller/ # Handles API requests (register, login)
│ │ ├── filter/ # Custom JWT filter (extends OncePerRequestFilter)
│ │ ├── model/ # Entity classes
│ │ ├── repo/ # JPA repositories
│ │ ├── service/ # Business logic + JWT utilities
│ │ └── config/ # Security configuration
│ └── resources/
│ └── application.yml # Database & security configs
└── test/

## ⚙️ How It Works
1. **Register a User**
   - Send a POST request to `/api/register` with name, email, and password.
   - User details are saved in the database.

2. **Login**
   - Send a POST request to `/api/login` with credentials.
   - If valid, server generates a **JWT token** and returns it.

3. **Access Protected Endpoints**
   - Include the token in the request header:
     ```
     Authorization: Bearer <your_jwt_token>
     ```
   - The `AppFilter` extracts and validates the token before granting access.

---

## 🔒 Security Flow Diagram
## 🧰 Example Endpoints
| Endpoint | Method | Description | Auth Required |
|-----------|--------|--------------|----------------|
| `/api/register` | POST | Register new user | ❌ No |
| `/api/login` | POST | Authenticate user & return JWT | ❌ No |
| `/api/users` | GET | Get all users | ✅ Yes |
| `/api/profile` | GET | Get logged-in user info | ✅ Yes |

---

## 🧪 Testing with Postman
- Add header:  
  `Authorization: Bearer <your_token>`
- Try accessing protected endpoints after login.

---

## 🧑‍💻 Author
**Somaraju Indukuri**  
Java | Spring Boot | REST APIs | Security | MySQL  

📫 Connect on [LinkedIn](https://www.linkedin.com/in/somaraju-indukuri-b5148a216/) or [GitHub](https://github.com/Somaraju080102)

---

## 🏁 License
This project is licensed under the MIT License.
