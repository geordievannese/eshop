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