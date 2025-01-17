# Writing Test Cases

## Unit Tests

To ensure that the code we've written is of high quality, we write unit tests. These tests are written to test the smallest unit of code, usually a function or method, and are written to ensure that the code behaves as expected. It should be independent of external dependencies, such as databases, networks, or other services. These dependencies should be mocked or stubbed out so that the tests can run in isolation.

It can be of multiple types:

- TDD (Test Driven Development)
  - TDD is an approach in which we write tests before writing the actual code. This helps us to think about the requirements and design of the code before writing it.
  - Process
    - Write a test
    - Run the test (It should fail)
    - Write the code
    - Run the test (It should pass)
    - Refactor the code (After writing tests and making them pass, the code might not be in the best possible shape. It might have duplication, it might not follow good design principles, or it might just be hard to read or understand.)
    - Run the test (It should pass)
    - Repeat
- Flaky tests
  - Flaky tests are tests that sometimes pass and sometimes fail. This is usually due to the test being dependent on some external factor, such as the network or the system clock.
    - For example, when we add numbers using multiple threads, the expectation is that the sum should be the same every time. But if the threads are not synchronized properly, the sum might be different every time. A test case written to assert the expected output might fail sometimes and pass sometimes, hence it is a flaky test.
  - Flaky tests could be due to Flaky code, but also due to Flaky infrastructure sometimes

## Integration Tests

Integration tests are written to test the interaction between different parts of the system. These tests are written to ensure that the different parts of the system work together as expected. They are usually written after the unit tests and are used to test the integration of the different units of code.

`A() -> B() -> C() -> D()`

In a production environment, we might have multiple services interacting with each other. Integration tests are written to test the interaction between these services and not just the individual units.

## Functional Tests

These tests are written to test the functionality of the system as a whole. They are written to ensure that the system behaves as expected from the user's perspective, and are usually written after the integration tests.

Example: Search Product + Add to Cart + Checkout

It is different from integration tests because it tests the system as a whole, and not just the interaction between different parts of the system.

## Non-Functional Tests

- Performance Testing
- Load Testing
- Stress Testing

## Test Pyramid

- Unit Tests (Bottom)
- Integration Tests (Middle)
- Functional Tests (Top)

The test pyramid is a way of thinking about how many tests you should have at each level of the testing pyramid. The idea is that you should have a lot of unit tests, some integration tests, and a few functional tests. This is because unit tests are fast and cheap to run, so you can have a lot of them. Integration tests are slower and more expensive to run, so you should have fewer of them. Functional tests are the slowest and most expensive to run, so you should have the fewest of them.

## What to test?

- Happy scenario
- Edge cases
- Negative scenarios

Companies like Coinbase give a simple DSA question, but they expect you to write test cases for all possible scenarios. With this they test that you can think of all possible scenarios and write a good logic.

> 5 principles of writing good test cases

- TCs should be fast, should follow AAA pattern. i.e. Arrange, Act, Assert
- TCs should be independent of each other or isolated
- Repeatable (produce the same result every time it is run)
- Self-validating
- Test behaviors not implementation

## Mocking

- Benefits
  - Repeatable
  - Isolated

When we're using Mocks, we're basically testing with Doubles.

Double is a generic term for any kind of pretend object used in place of a real object for testing purposes. There are several kinds of doubles, including:

- Mocks
  - Simplest and dumbest kind of double. It's like a Maniquin, once set, it will behave in that way
  - Rules are hardcoded which are always followed
- Stubs
  - In a stub, we implement the same interface as the external dependency. This can support some level of dynamic behavior.
  - ```java
    public class ProductController {
        private ProductService productService;
        public ProductController(ProductService productService) {
            this.productService = productService;
        }
        public void addProduct(Product product) {
            productService.addProduct(product);
        }
    }
    ```

    In the above example, if we hadn't used Dependency inversion principle, we would have to use the actual ProductService class. But since we're using it, we can use a stub class which implements the ProductService interface.
- Fakes
  - This is more complicated or sophisticated than a stub. They are going to be closer to the real thing.
  - ```java
    public class FakeProductService implements ProductService {
        private HashMap<Long, Product> products = new HashMap<Long, Product>();
        @Override
        public void addProduct(Product product) {
            Long id = 0; // some id generation logic
            products.add(id, product);
        }
        @Override
        public List<Product> getProducts(Long id) {
            return products.get(id);
        }
    }
    ```

    Here the usage of a HashMap is quite near to the actual implementation, but it's not the actual implementation. It's a fake implementation.

| Mock | Stub             | Fake                 |
| ---- |------------------|----------------------|
|Will be easy | More complicated | Sophisticated        |
|Hacky | More dynamic | Closer to real thing |
| More Hardcoded | Less Hardcoded | Least Hardcoded      |
| Easier to write | Harder to write | Hardest to write     |

> **Interview Question**: What is the difference between Mock, Stub, and Fake?

In the context of testing, Mocks, Stubs, and Fakes are types of test doubles that are used to replace real components in the system under test. Here's how they differ:

- **Mock**: A Mock is the simplest kind of test double. It is an object that has preset expectations and responses as defined in your test. It does not have any logic, it simply returns what you program it to return and can verify if its methods were called as expected. Mocks are typically used to test interactions and behavior.
- **Stub**: A Stub is a bit more sophisticated than a mock. It's an implementation of an interface that returns specific results that are convenient for the test. Stubs can't verify how they are used, they just respond to calls as programmed. Stubs are typically used to isolate the system under test from its dependencies.
- **Fake**: A Fake is a working implementation that takes shortcuts for the sake of simplicity, speed, and control in tests. For example, a Fake might use an in-memory database instead of a real database. Fakes have actual behavior, but the behavior is implemented in a way that's convenient for testing.

In summary, the main difference between a Mock, a Stub, and a Fake is the complexity and the purpose. A Mock is used for verifying interactions, a Stub is used for isolating the system under test, and a Fake is used for simulating real behavior in a controlled way.
