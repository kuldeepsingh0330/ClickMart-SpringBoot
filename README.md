<p align="center">
  <img src="https://github.com/kuldeepsingh0330/ClickMart-SpringBoot/assets/95225751/a7597034-5cb0-4f82-af82-4065b73618c0" alt="Logo">
</p>



# ClickMart E-Commerce Application

ClickMart is a Spring Boot-based e-commerce application that allows users to browse and purchase products, manage their wishlist, and view recent offers. It provides features for user authentication, category and product management, payment processing, and more. It is a full-stack Ecommerce application developed using Spring Boot for the backend and Android for the frontend. Both JWt authentication and form log-in used in this project of SpringBoot.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Screenshots and Video](#screenshots-and-video)
- [Contributing](#contributing)

## Features

- User authentication and login functionality.
- Browse products by category and search for products.
- View detailed product information and product images.
- Add and remove products to/from the wishlist.
- Process payments using the Razorpay payment gateway.
- View and manage recent offers.
- Admin functionalities for category management, product management, and order management.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- MySQL Database
- JPA 
- JWT (JSON Web Tokens) for authentication
- Razorpay for payment processing

## Screenshots and Video

### /admin/login
![login](https://github.com/kuldeepsingh0330/ClickMart-SpringBoot/assets/95225751/55d1831c-1d7c-4c47-aac5-f1672be39055)

### /admin/category
![HOME](https://github.com/kuldeepsingh0330/ClickMart-SpringBoot/assets/95225751/8c85b432-46cc-4400-8cc1-194f2e107ec3)

### /admin/order
![orders](https://github.com/kuldeepsingh0330/ClickMart-SpringBoot/assets/95225751/0567fa22-1e64-469d-9530-db3ec16c773c)

### /admin/feedback
![feedback](https://github.com/kuldeepsingh0330/ClickMart-SpringBoot/assets/95225751/ce189b69-97e1-4ed4-897d-3bd584775cf4)


https://github.com/kuldeepsingh0330/ClickMart-SpringBoot/assets/95225751/f9f7cf39-8df4-4eb5-b06e-1cc5d5f18c4f


## Getting Started

1. Clone the repository:

   ```sh
   git clone https://github.com/kuldeepsingh0330/ClickMart-SpringBoot.git
2. Import the project into your preferred IDE (e.g., VS Code, SpringToolSuite IntelliJ IDEA, Eclipse).

3. Configure your MySQL database and Razorpay settings in `src/main/resources/application.properties`.

4. Run the Spring Boot application.

## Configuration

- **Database Configuration:** Set your MySQL database connection details in the `application.properties` file.

- **JWT Configuration:** Configure your JWT secret key and token expiration time in the `application.properties` file.

- **Razorpay Configuration:** Set your Razorpay API key and secret key in the `application.properties` file.

- **File Uploads:** Configure the maximum file size for uploads in the `application.properties` file.


## Usage

1. Access the application at: http://localhost:8080

2. Register a new user account or log in using existing credentials.

3. Browse products by category, search for products, and view product details.

4. Add products to your wishlist and manage your wishlist items.

5. Proceed to checkout and complete payments using the Razorpay gateway.

6. Admin users can manage categories, products, orders, and view feedback.




## Contributing

Contributions to the ClickMart project are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.





