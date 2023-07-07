This project implements a simple Order Management System using Spring Boot that follows the principals of Test-Driven Development. It also allows users to handle CRUD operations for orders.

1. Setup the development environment by including web, hpa, and h2 dependencies.
2. Create the Order entity that includes Id, customerName, orderDate, shippingAddress, and total.
3. Create the test-driven development test cases to test all of CRUD operations such as create, read, update, and delete.
- When you run the tests, you should see at least 6 pass with insertions, deletions, and selections.
4. Build the rest api tests
  - When you run the rest api test, you should see the http response bring back the body. 
5. Create the test cases for implementing validations
  - Validation constraints are added onto the Order entity.
  - When running the test you will see a 404 error which means a bad request.
6. Create the test cases for error handling
- You will see a statement when running the test that says "Order with ID 999 not found" in the body.
  
  
