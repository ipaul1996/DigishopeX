<h1 align="center">DigishopeX E-commerce RESTful Web Services</h1>

<p align="center">
  <img width="390" height="380" src="https://github.com/ipaul1996/DigishopeX/blob/master/Resources/DigishopeX%20Logo.png">
</p>

## Project Description
This application provides a comprehensive set of RESTful web services that can handle various operations of an E-commerce website. There are three types of users - **_Admin_**, **_Customer_**, and **_Guest-User_**, each with their own set of privileges and functionalities.

**Admin Features:**
- Admins can register as an admin and gain the highest level of access to the website.
- They can create product categories and add products to categories.
- Admins can manipulate the list of products and register suppliers and shippers.
- They can change the active status of suppliers and shippers.
- Admins can view and track orders, obtain sales analysis, and view bestselling products.
- The platform gives admins complete control over managing products, suppliers, and customers.

**Customer Features:**
- Customers can register themselves on the platform and view available categories and products.
- They can sort and filter products according to their preferences.
- Customers can add, update, and delete products from their cart.
- They can place orders, cancel orders, and request returns.
- Customers can track their order status, rate products, and access other functionalities specific to them.


**Guest-User Features:**
- Guest-users can view available products.
- They can sort and filter products according to their preferences.

The application is secured with **_Spring Security_**, which provides robust authentication and authorization mechanisms using **_JSON Web Token_** to ensure that users are only able to access the functionalities that they are authorized to use. **_CORS_** feature is also incorporated so that the web services can be accessed through any type of client, making it easier for users to interact with the application.
Overall, this RESTful web service provides extensive functionalities to perform various **_CRUD_** operations required in an E-commerce application, making it an ideal choice for businesses that want to create a scalable and efficient E-commerce platform.

👉 [Click here]() to access a detailed video explanation of this project.

## Project Structure

<p align="left">
  <img width="554" height="506" src="https://github.com/ipaul1996/DigishopeX/blob/master/Resources/project_structure.PNG">
</p>


## Technologies and Tools Used 

- Java
- MySQL
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- lombok
- Maven
- Git & GitHub
- Spring Tool Suite
- Postman
- Swagger


## ER Diagram
👉 [Click here](https://github.com/ipaul1996/DigishopeX/blob/master/Resources/er4.png) to view **ER Diagram**


## API Root Endpoint
`http://localhost:8088/`

`http://localhost:8088/swagger-ui/index.html#/`

## Usage Instructions
- To run the DigishopeX server in your local machine, you have to update database configuration inside the [application.properties](https://github.com/ipaul1996/DigishopeX/blob/master/Resources/application.properties) file as per you local database config.
- All endpoints accept and return JSON data. <p align="left">
  <img width="436" height="297" src="https://github.com/ipaul1996/DigishopeX/blob/master/Resources/json_snippet.PNG">
</p>
- Use caching to reduce number of API calls.

## API Documentation
👉 [Click here](https://github.com/ipaul1996/DigishopeX/blob/master/Resources/DigishopeX-api-documentation.pdf) to view **API Documentation**



## Feedback
Valuable feedback will be appreciated.
You can reach out to me via LinkedIn.
[Let's Connect...](https://www.linkedin.com/in/indrajitpaul1996/)


