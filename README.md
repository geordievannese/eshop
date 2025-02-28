# #Reflection 1

I incorporated several clean code principles discussed in class, such as using meaningful and descriptive naming conventions. For instance, I ensured that a variable like productID clearly represents its purpose as the identifier for a product. I also adhered to the Single Responsibility Principle (SRP) by clearly separating responsibilities across the controller, service, and repository layers. To minimize code duplication, I reused existing logic within service and repository methods. Additionally, I applied secure coding practices learned in class, such as implementing confirmation prompts for delete actions.

I recognized opportunities for further improvement, including introducing input validation with annotations like @NotNull and @Size, as well as enhancing error handling by creating custom error pages. I also aim to strengthen security by adding authorization checks to restrict unauthorized access to editing or deleting endpoints.

# #Reflection 2

1. After writing the unit tests, I feel more confident in our application's stability, knowing that the core functionalities, edge cases, and error handling are covered; however, while achieving high or even 100% code coverage is encouraging, it doesn't guarantee that our code is entirely free of bugs, as code coverage only measures which lines are executed rather than the quality or comprehensiveness of the tests themselves.


2. When creating another functional test suite that verifies the number of items in the product list and shares similar setup procedures and instance variables with the CreateProductFunctionalTest, the duplication of code becomes a concern, as it violates the DRY (Don't Repeat Yourself) principle, potentially reducing maintainability and overall code quality; to improve this, common setup routines and configuration code should be refactored into a shared base class or utility, thereby centralizing changes and ensuring consistency across all test suites.


# # 4.2 Reflection

Code Quality Issues Fixed
1. I made a small improvement to the contextLoads test in the application by adding descriptive comments. This change clarifies that the test's purpose is to ensure that the Spring Boot application context loads successfully, even though no additional logic was necessary. This minor update enhances code readability and helps future developers understand the test's intent.


2. CI/CD Workflow Reflection
The current CI/CD implementation meets the core principles of Continuous Integration and Continuous Deployment. The workflows automatically run test suites and perform code quality analysis on every commit, which ensures immediate feedback and seamless integration of new changes without compromising stability. Additionally, the automated deployment to a PaaS guarantees that validated changes are quickly and reliably released to production. Overall, this setup minimizes the risk of introducing defects and aligns well with modern agile practices, fostering rapid and dependable delivery of software updates.


# # Module 3
In this project, I applied several SOLID principles to improve maintainability and flexibility. I used the Single Responsibility Principle (SRP) by creating separate controllers for each entity—such as a ProductController for products and a CarController for cars—thereby avoiding combining multiple responsibilities within one class. I also embraced the Open/Closed Principle (OCP) by designing controllers to be independent and free from unnecessary inheritance; if shared functionality is needed, it is extracted into a common utility or a base controller, allowing for easy extensions like adding a new BikeController without modifying existing code. To adhere to the Liskov Substitution Principle (LSP), I removed inappropriate inheritance relationships, ensuring that controllers either extend a base abstract class or use annotations so that they can be substituted without affecting system behavior. Additionally, the Dependency Inversion Principle (DIP) is implemented by injecting services through their interfaces (for example, using CarService rather than a concrete implementation like CarServiceImpl), which decouples high-level modules from low-level details. This approach brings significant advantages: each class having a single responsibility simplifies debugging and maintenance, independent controllers ease the process of extending features safely, and reliance on abstractions increases testability and flexibility by allowing easy swapping of implementations. Conversely, neglecting these principles can lead to high coupling and complex maintenance, where changes in one part of the code might inadvertently affect another, difficulty in extending features without introducing bugs, brittle inheritance relationships that cause unintended behavior, and rigid dependency management that complicates switching implementations.