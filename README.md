Full-Stack Spring Boot & Angular Application
Overview
This full-stack web application combines a robust Spring Boot backend with a dynamic, responsive Angular frontend. Built with a modern, scalable architecture, it delivers RESTful APIs and an intuitive user interface for e-commerce-like functionality. The application supports user authentication, product management, order processing, and bill handling, tailored for both admin and user roles.
Key Features
Admin Features

Admin Dashboard: Centralized interface for managing application data.
Category Management: Create, edit, and filter product categories.
Product Management: Add, update, delete, and filter products.
Order Management: Create and manage customer orders.
Bill Management: View bill details, download bills, cancel bills, and filter products.
User Management: Monitor users and filter user lists.
Security: Securely update admin password.

User Features

Authentication: Secure login and sign-up using JWT-based authentication.
User Dashboard: Personalized interface for user activities.
Order Management: Place and manage orders.
Bill Management: View bill details, download bills, cancel bills, and filter products.
Profile Management: Update user profile information.
Security: Securely update user password.

Technologies
Backend

Java 17: Core programming language.
Spring Boot: Framework for building the backend.
Spring Data JPA: Simplified database operations.
Spring Security: Robust security with JWT authentication.
Hibernate: ORM for database interactions.
Maven: Dependency management and build tool.

Frontend

Angular: Framework for dynamic, responsive UI.
TypeScript: Enhanced JavaScript for type-safe coding.
Angular CLI: Streamlined development and build processes.

Database

Oracle: Reliable relational database for data persistence.

Tools

Docker: Containerization for consistent deployment.
DBeaver: Database management and querying.
Tomcat: Application server for deployment.
Postman: API testing and validation.

Project Structure
├── backend/                       # Spring Boot backend
│   ├── src/main/java/            # Java source code
│   ├── src/main/resources/       # Configuration files (e.g., application.properties)
├── frontend/                      # Angular frontend
│   ├── src/app/                  # Angular components, services, and modules
│   ├── src/assets/               # Static assets (images, styles)
├── docker-compose.yml             # Docker configuration for containerized deployment
└── README.md                     # Project documentation

Prerequisites

Java 17: Ensure JDK 17 is installed.
Node.js & npm: Required for Angular frontend (Node.js v16+ recommended).
Angular CLI: Install globally using npm install -g @angular/cli.
Maven: For building the Spring Boot backend.
Oracle Database: Configured and running.
Docker: Optional, for containerized deployment.
Postman: Optional, for API testing.

Setup Instructions
Backend Setup

Clone the Repository:git clone <repository-url>
cd backend


Configure Database:
Update src/main/resources/application.properties with your Oracle database credentials.

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_username
spring.datasource.password=your_password


Build and Run:mvn clean install
mvn spring-boot:run

The backend will run on http://localhost:8080.

Frontend Setup

Navigate to Frontend:cd frontend


Install Dependencies:npm install


Run the Application:ng serve

The frontend will run on http://localhost:4200.

Docker Setup (Optional)

Ensure Docker is installed and running.
Run the application using Docker Compose:docker-compose up --build



API Endpoints

Authentication: POST /api/auth/login, POST /api/auth/signup
Products: GET /api/products, POST /api/products, PUT /api/products/{id}
Orders: GET /api/orders, POST /api/orders
Bills: GET /api/bills, POST /api/bills/cancel/{id}
Users: GET /api/users, PUT /api/users/profile

Use Postman to test these endpoints with appropriate payloads.
Usage

Admin Access:
Log in with admin credentials to access the admin dashboard.
Manage categories, products, orders, bills, and users.


User Access:
Sign up or log in to access the user dashboard.
Place orders, view bills, and manage profile information.



Contributing

Fork the repository.
Create a feature branch (git checkout -b feature/your-feature).
Commit changes (git commit -m 'Add your feature').
Push to the branch (git push origin feature/your-feature).
Create a pull request.

License
This project is licensed under the MIT License. See the LICENSE file for details.
