
# 🍽️ Full-Stack Restaurant Management System  
### Built with **Spring Boot** & **Angular**

---

## 📖 Overview  
Step into the world of smart dining with this robust full-stack Restaurant Management System, designed to streamline operations like menu management, order tracking, and user roles.
Tailored for restaurant administrators and staff, this system ensures real-time control over food categories, product listings, and customer orders.

At its core, a Spring Boot backend powers a scalable and secure REST API architecture, while an intuitive Angular frontend delivers a modern and responsive user experience.

The system now includes an intelligent Category Recommendation engine that dynamically identifies and highlights the most frequently ordered food category, using optimized Oracle SQL and smart Redis cache eviction to ensure both accuracy and performance.

---

## 🔑 Key Features

### 🛠️ Admin Panel
- 📊 **Dashboard**: Centralized interface to manage all operations  
- 🍱 **Category Management**: Add, update, and filter product categories  
- 🍔 **Product Management**: Create, edit, delete, and manage products with search/filter  
- 🧾 **Order Management**: Track, update, and manage customer orders  
- 🔐 **Security Controls**: Change admin credentials and enforce role-based access
- 📨 **Contact System (NEW)**

### 🙋 User Functionality
- 🔑 **Authentication**: JWT-secured login and registration  
- 🏠 **User Dashboard**: Personalized view for order history and profile  
- 🛍️ **Place Orders**: Add products to cart and create new orders  
- 📝 **Profile Management**: Update user details and change password
- 📨 **Contact System (NEW)** : Allow users to submit inquiries and enable admins to respond with comment threads and status tracking
- 💡 **Category Recommendation** : Automatically highlights the most popular food category based on customer order history (with smart cache handling).
---

## 🏗️ Backend Highlights
- ✅ Layered Architecture (`Controller → Service → Repository`)  
- 📦 DTOs & Entity Mapping for clean data handling  
- ⚠️ Exception Handling & API Validation  
- 🛡️ Secure REST APIs with JWT Authentication  
- 📘 API Documentation via Swagger / OpenAPI  
- 🧹 Clean Code Practices and modular services
- 💡 Smart Category Recommendation with Oracle-native SQL and conditional Redis cache eviction

---

## 🧰 Tech Stack

### 🔧 Backend
- `Java 17`
- `Spring Boot`
- `Spring Security`
- `Spring Data JPA`
- `Hibernate`
- `JWT Authentication`
- `Redis` (for caching)
- `Maven`
- `JDBC`
- `RESTful APIs`
- `Flyway`

### 🎨 Frontend
- `Angular`
- `TypeScript`
- `Angular CLI`
- `Angular Material`

### 🗄️ Database & Tools
- `Oracle Database`
- `Docker` (containerization)
- `DBeaver` (DB GUI Tool)
- `Postman` (API Testing)
- `Tomcat` (Spring Boot embedded server)

### 🏗️ Project Structure


```
restaurant-management-system/
├── back-end/                        # 🖥️ Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/restaurant/
│   │   │   │   ├── config/          # Spring configuration classes
│   │   │   │   ├── controller/      # REST Controllers
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── entity/          # JPA Entities
│   │   │   │   ├── mapper/          # Model mappers
│   │   │   │   ├── repository/      # JPA Repositories
│   │   │   │   ├── service/         # Business logic
│   │   │   │   ├── security/        # Security configurations
│   │   │   │   ├── util/            # Utility classes
│   │   │   │   └── vm/              # View Models (optional)
│   │   │   └── resources/
│   │   │       ├── application.yml  # Spring Boot config
│   │   │       ├── db.migration/    # Database migration scripts
│   │   │       ├── i18n/            # Internationalization
│   │   │       ├── static/          # Static files
│   │   │       └── templates/       # Thymeleaf templates
│   └── pom.xml                      # Maven Project File
│
├── front-end/                       # 🌐 Angular Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/          # Angular components
│   │   │   ├── models/              # TypeScript models/interfaces
│   │   │   ├── services/            # API and logic services
│   │   │   ├── guards/              # Route guards
│   │   │   ├── interceptors/        # HTTP interceptors
│   │   │   └── shared/              # Shared modules/components
│   ├── assets/                      # Static assets
│   └── environments/                # Environment configs
│
├── docs/                            # Documentation
│   └── api-docs.md                  # Swagger/OpenAPI docs
│
├── assets/                          # Screenshots & images
│   ├── home-page.png
│   ├── login-page.png
│   ├── signup-page.png
│   └── system-banner.png
│
└── README.md                        # You are here 📖
```
