Full-Stack Spring Boot & Angular Application
Overview
This full-stack web application leverages Spring Boot for a robust backend and Angular for a dynamic, responsive frontend. Designed with a modern, scalable architecture, it provides RESTful APIs and an intuitive user interface. The application supports e-commerce-like functionality, including user authentication, product and order management, and bill processing, tailored for both admin and user roles.
Key Features
Admin Features

Admin Dashboard: Centralized interface for managing application data.
Category Management: Add, edit, and filter product categories.
Product Management: Create, update, delete, and filter products.
Order Management: Create and manage customer orders.
Bill Management: View bill details, download bills, cancel bills, and filter products.
User Management: Ping users and filter user lists.
Security: Change admin password securely.

User Features

Authentication: Secure login and sign-up with JWT-based authentication.
User Dashboard: Personalized interface for user activities.
Order Management: Place and manage orders.
Bill Management: View bill details, download bills, cancel bills, and filter products.
Profile Management: Update user profile information.
Security: Change user password securely.

Technologies

Backend: Java 17, Spring Boot, Spring Data JPA, Spring Security, Hibernate, Maven
Frontend: Angular, TypeScript, Angular CLI
Database: Oracle for reliable data persistence
Tools: Docker for containerization, DBeaver for database management, Tomcat for deployment, Postman for API testing



Project Structure
├── backend/
│   ├── src/main/java/          # Spring Boot application code
│   ├── src/main/resources/     # Configuration files (application.properties)
├── frontend/
│   ├── src/app/                # Angular components, services, and modules
│   ├── src/assets/             # Static assets (images, styles)
├── docker-compose.yml           # Docker configuration (optional)
└── README.md                   # Project documentation
