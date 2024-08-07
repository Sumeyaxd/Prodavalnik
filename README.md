# Prodavalnik - project for JavaWeb - SoftUni

## Overview

The **Prodavalnik** is a robust e-commerce platform built with Spring Boot, delivering a comprehensive suite of features to facilitate product management, order processing, user administration, and review handling. It integrates email services and scheduled tasks, ensuring seamless operations and an enhanced shopping experience for users. Designed with both customers and administrators in mind, Prodavalnik offers efficient tools and functionalities for effective e-commerce management, ensuring a smooth and enjoyable online shopping journey.
## Key Features

- **User Management**: Register, update, and delete users with secure password encoding and role-based access control.
- **Order Processing**: Create orders, view orders by date or user, and manage order statuses.
- **Cart Management**: Add products to the cart and manage product stock quantities.
- **Reviews**: Add, view, and delete reviews for products.
- **Email Notifications**: Automated email services for successfully registered user and order confirmations.
- **Security**: Authentication and authorization using Spring Security.

## Screenshots

Here are some visualizations of the website:

<div class="image-container">
  <p>Home page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.53.06.png" alt="Screenshot 2024-08-05 143731"/>
</div>
<div class="image-container">
  <p>Login page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.53.43.png" alt="Screenshot 2024-08-05 143731"/>
</div>
<div class="image-container">
  <p>Register page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.53.57.png" alt="Screenshot 2024-08-05 143731"/>
</div>
<div class="image-container">
  <p>Products page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.56.31.png" alt="image"/>
</div>
<div class="image-container">
  <p>About page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.11.06.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>User profile page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.55.02.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Admin profile page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.12.07.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Update profile page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.45.43.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Comments section:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.59.31.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Add comment page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.45.54.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>One of the shop page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.58.14.png"/>
</div>
<div class="image-container">
  <p>Only for administrator pages: Add supplier page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 18.58.14.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Only for administrator pages: Suppliers:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.01.46.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Only for administrator pages: Add supplier page:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.51.37.png" alt="Screenshot 2024-08-05 144432"/>
</div>
<div class="image-container">
  <p>Only for administrator pages: All made orders:</p>
  <img src="src/main/resources/static/images/screenshots/Screenshot 2024-08-07 at 19.51.49.png" alt="Screenshot 2024-08-05 144432"/>
</div>




## Technologies Used

- **Spring Boot**: Core framework for building the application.
- **Spring Data JPA**: Data access layer.
- **Spring Security**: Security and authentication.
- **ModelMapper**: Object mapping.
- **Java Mail**: Email services.
- **Thymeleaf**: Template engine for email templates.
- **MySQL**: Primary databases for production.
- **Java GreenMail**: Library for testing email functionality

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- MySQL or PostgreSQL database

## To Run the Application:

- Ensure you have Java and Gradle installed.
- Clone the repository and navigate to the project directory.
- Necessary environment variables: MAIL_USERNAME, MAIL_PASSWORD
- Use `mvn spring-boot:run` to start the application.
- Access the application via [http://localhost:8080](http://localhost:8080).
- Access the REST API application via [here](https://github.com/Sumeyaxd/Prodavalnik-Supplier/tree/main) server port: [http://localhost:8081](http://localhost:8081)
## Database Configuration:

- Ensure to configure the database settings in `application.yaml` for proper persistence.

