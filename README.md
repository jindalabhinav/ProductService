# Product Catalogue Service

**PRD (Product Requirements Document) -** https://docs.google.com/document/d/1Gn2ib5YhhpcFUiWGAUbCpg0ZPh3m_wSA-9IolGMjkIE/edit#heading=h.hteovoit9b96

This is a service that handles all products.

## Class Diagram

```mermaid
classDiagram
    class Product {
        - id
        - title
        - description
        - price
        - image
        - category : Category
        - rating
    }


    class Category {
        - id
        - name
    }
```

> How can we Inject Dependencies?

By using the `@Autowired` annotation. This can be used in 3 ways:

1. **Constructor Injection:** This is the most preferred and readable approach. Use the annotation on top of a Constructor.
2. **Field Injection:** This is not preferred as it makes the code less readable and difficult to test. Use the annotation on top of a Class Field.
3. **Method Injection:** This is also not preferred as it makes the code less readable and difficult to test. Use the annotation on top of a Class Method.

> If the contract from the third party changes, should it impact our whole application?

No. It should not impact everything, and for this reason, we create DTOs. To interact with any third-party application.

> What does a JSON most resemble to?

`Map<String, Object>`
```
Map<id, 1>
Map<title, "bag">
...
```

But here, if the key name changes a bit, or if the value's datatype changes, the code will break, and it's difficult to communicate this contract to a client.

Instead, we can use an Object Mapper, that maps the JSON map to the desired class object.

