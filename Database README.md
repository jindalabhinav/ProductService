# Product Service

This document explains how a request is sent to the database and how it is handled:

1. SQL Query
2. Connect to the Database
3. Run the Query
4. Get Row and Parse into required Object
5. Use this Object further

This process is simplified by using ORMs.

## ORM (Object Relational Mappers)

ORMs provide an easy way to work with databases and convert data into objects (classes). They can help us to:

- Write queries
- Convert results to objects
- Perform joins

With an ORM, we can:

- Convert an entity (like `Product`) into a new table
  - This saves us from writing the `CREATE TABLE...` SQL queries
- `User - findByName("Abhinav")` - A simple method such as this will get converted into appropriate SQL queries
- etc.

Different databases like MySQL, Postgres, MongoDB, have different ORM Drivers which connect to the database. To make use of these different drivers in a loosely coupled way, we use JDBC, which is an interface that provides a common convention for connecting and interacting with a database.

> JDBC -> Java Database Connectivity

On top of this, we need a platform or another layer to create the queries and execute them.

Example: `findByName("Abhinav")` --> `SELECT * FROM User WHERE name = "Abhinav"`

This is taken care of by the ORMs like Hibernate, MyBatis, etc.

Now these ORMs are external to the application, and we need a way to make them loosely coupled. For that, we use an interface called **JPA (Java Persistence API)**.

In a nutshell:

- Client connects to JPA (that abstracts everything else from the Client)
- JPAs (like Spring JPA) interfaces with different ORMs like:
  - Hibernate
  - MyBatis
- Each of these ORMs work with JDBC
- Based upon how many databases they support, they'll have different drivers like:
  - MySQL Driver
  - Postgres Driver
  - Etc.
- These Drivers will interact with the underlying Database

## Repository Pattern

Usual Flow: Client --> Controller --> Service --> Repository --> Db

The repository layer is also called DAO (Data Access Objects) at some places.

> So what is the Repository Pattern?

The code to interact with the persistence layer should be separate from the application layer.

## UUID (Universally Unique Identifier)

- While registering at Scaler, students have to be added to the Student Tables.
- A primary key is needed for the same.
  - Email? Could be.
    - Disadvantages
      - Space
      - Storage takes more time
      - Indexing will take time
      - Email Id can change as it is a user attribute
    - Not a good Identifier
  - Some Integer/Long as PK? Needs autoincrement.
    - Disadvantages
      - Overflow
      - If the database is distributed, then the PK should be in sync across machines
  - What if my ID is a combination of some details that are unique to a user
    - timestamp + mac_id + ip_addr + serial_no + .....
      - More parameters, lesser collision

This is the concept of a UUID. It's of 128-bit, denoted in a hexadecimal format.

> Why Hexadecimal?

Using Binary format, we'll need 128 binary digits to represent a UUID, whereas with Hexadecimal, which is a 16-base compared to a 2-base number system in Binary, 1 hex digit can store the same amount of information as 4 bits (2^4 = 16), thus saving space when stored in the database.

## Inheritance in Database

Scaler has Users having

- ID
- Email
- Password

Different types of Users

- Students (extends User)
  - Classes
- Instructors (extends User)
  - Rating
- Mentors (extends User)
  - Sessions
- TAs (extends User)
  - HelpRequests

Each User type has some specific attributes, on top of the common attributes from above. And to create Tables using JPA, we can annotate these classes with `@Entity`.

### @MappedSuperClass

Now Scaler will never have just a normal User, it is always going to be of a specific type. Hence, we don't need a Table for User, but rather for its child classes such as Students and Mentors, which have some common columns from the User class. For this, we'll annotate the User class with `@MappedSuperClass` and not `@Entity`.

> Cons

- I cannot create a Table for the User class as well if there is such a requirement
- How to get all the list of Users
  - Using Joins or Unions

### @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

In case I want the Tables for all the classes in the hierarchy, I can use `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`.

### @Inheritance(strategy = InheritanceType.JOINED)

> How do we get all the Users?

This strategy is used when we want to get all the Users, irrespective of their type (when bulk data is more useful for us). We can use Joins or Unions to get Students, Mentors, etc., specific data together.

It will create all the columns from the Users class, and the tables for the subclasses will have only the columns specific to them. The tables will be joined using the primary key of the parent class, present as a foreign key in the child class tables. This is the most used form of Inheritance strategy.

### @Inheritance(strategy = InheritanceType.SINGLE_TABLE)

If we need all the data in a single table, be it Users, Students, or Mentors, we can use this strategy. It will create a single table with all the columns from the parent and child classes. The child class columns will be nullable. Also, it'll have a discriminator column to differentiate between the different types of Users.

This is the most denormalized form of data storage which increases the read performance since all the data is present in a single table, but with a lot of sparse data. We can also not put any non-null constraints on the child class columns.

## ORM

We don't want to write SQL queries, but we want to interact with the database. This is where ORMs come into play. We just need to write Java code and the ORM will convert it into SQL queries.

### Query Methods

1. Declared Queries
   - We just write the method name, and the implementation is taken care of by the ORM
   - <https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html>
2. HQL Queries
    - Hibernate Query Language
    - Similar to SQL, but it works on the Java Objects
      - SQL queries are a little different for different databases, but Hibernate works for all of them
    - A unique query language can help for all the databases
    - Ex: findDistinctByIdAndDescriptionSortByRatingDesc
      - Now this seems quite complex when we read the method name
      - In such complex cases, creating our own query can be helpful
    - If we write them in Native language, for let's say MySQL, it'll be difficult to migrate to another Db such as Postgres
    - To avoid this, we can use HQL
    - HQL is nothing but a modified version of SQL, in OOP format. On the Database, the native QL will be used which will be transformed by JPA
    - Also, in this case, function name will not matter because we're writing our own query

   ```java
    @Query("select p.id, p.title from Product p where p.title = :title")
    List<ProductWithIdAndTitle> foo2(@Param ("title") String title);
    ```

3. Native Queries
    - We can write SQL queries directly
    - We can use the `@Query` annotation with `nativeQuery = true` to write Native queries

   ```java
    @Query(value = "select p.id, p.title from Product p where p.title = :title", nativeQuery = true)
    List<ProductWithIdAndTitle> foo2Native(@Param ("title") String title);
    ```

### Cardinality

> Relationship between Product and Category

- One Product can have One Category
- One Category can have Many Products

Cascading is a concept where if we perform some operation on one entity, the same operation is performed on the associated entity as well.

Example:

```java
@ManyToOne(cascade = CascadeType.PERSIST)
private Category category;
```

The `@ManyToOne` annotation in JPA is used to establish a many-to-one relationship between two entities. The cascade attribute of this annotation defines the set of cascade operations that should be propagated to the associated entity.

- CascadeType.PERSIST: This means that if the owning entity is persisted (saved), then the associated entity should also be persisted. In your case, if a Product is saved, then the associated Category should also be saved.  
- CascadeType.ALL: This is a shorthand that includes all cascade operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH). This means that any operation performed on the owning entity will also be propagated to the associated entity. In your case, if a Product is saved, deleted, updated, refreshed or detached, then the associated Category will also undergo the same operation.

It's important to note that CascadeType.ALL might not be suitable for all situations. For example, you might not want to delete a Category just because a Product was deleted. In such cases, it would be better to use a more specific cascade type like CascadeType.PERSIST or CascadeType.MERGE.

### Fetch Type

```java
@Entity
public class Category extends BaseModel {
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<Product> products;
}
```

In JPA, when you retrieve an entity from the database, it can have relationships to other entities. The FetchType determines when these related entities should be loaded.

- `FetchType.EAGER`: This means that the related entities should be loaded immediately when the owning entity is loaded. This is done using a JOIN query to fetch the owning entity and its related entities in a single database round trip.  For example, consider the following entities:

    ```java
    @Entity
    public class Category {
        @Id private Long id;
        private String name;
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
        private List<Product> products;
    }
    
    @Entity
    public class Product {
        @Id private Long id;
        private String name;
        @ManyToOne private Category category;
    }
    ```

    If you load a `Category` from the database, JPA will immediately load all the `Product` entities related to that `Category` as well. This is done in a single database query using a `JOIN clause`.  
- `FetchType.LAZY`: This means that the related entities should be loaded only when they are explicitly accessed. This is done using a separate query to fetch each related entity when it is accessed.  For example, consider the following entities:

    ```java
    @Entity
    public class Category {
        @Id private Long id;
        private String name;
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
        private List<Product> products;
    }
    
    @Entity
    public class Product {
        @Id private Long id;
        private String name;
        @ManyToOne private Category category;
    }
    ```

    If you load a `Category` from the database, JPA will not load the `Product` entities related to that `Category`. Instead, the `products` field will be a proxy object. When you access this field (for example, by calling `category.getProducts()`), JPA will execute a separate database query to load the `Product` entities.

The choice between `FetchType.EAGER` and `FetchType.LAZY` depends on your specific use case. `FetchType.EAGER` can be more efficient if you know that you will need the related entities every time you load the owning entity. However, it can also lead to loading more data than necessary, which can be a performance issue. On the other hand, `FetchType.LAZY` can be more efficient if you often load the owning entity without needing the related entities. But it can also lead to the "N+1 selects problem" where you end up executing a large number of queries.

### Fetch Mode

> Note: JPA ignores Fetch Mode, but we need to know this for the N + 1 problem. A good interview question.

Let's say we have a Category which has a list of Products. How do we fetch a Category?

```java
List<Category> categories = categoryRepository.findByName(x);
for (Category category : categories) {
    List<Product> products = category.getProducts();
    for (Product product : products) {
        System.out.println(product.getTitle());
    }
}
```

How do we get this data from the Database?

1. We could get it in 1 query using Joins
2. We could get it in 2 queries, first we can get all the Categories, and then for each Category, we can get all the Products

    ```SQL
    SELECT Category.id FROM Category WHERE name = x;
    ```

    ```SQL
    SELECT * FROM Product WHERE category_id IN (<<categoryIds>>);
    ```

3. Instead of using the `IN` operator above, if there are `M categories`, we can use `M queries` to get all the Products for each Category

    ```SQL
    SELECT * FROM Product WHERE category_id = <<categoryId>>;
    ```

   And `1 query` to get all the Categories

    ```SQL
    SELECT * FROM Category WHERE name = x;
    ```

   `This is known as the n + 1 select problem. Executing one query will trigger n additional queries.`

With this we can say that how we're executing the query makes a lot of difference. Some are more efficient than others.

This behavior is controlled by Fetch Mode. It can be of the following types:

1. `FetchMode.JOIN`: This is the default mode. It will fetch all the data in a single query.
2. `FetchMode.SELECT`: This will fetch the data in multiple queries. This is useful when we want to fetch the data in a lazy manner.
3. `FetchMode.SUBSELECT`: This is similar to `FetchMode.SELECT`, but it will fetch the data in a single query. This is useful when we want to fetch the data in a lazy manner, but in a single query.

JPA ignores the Fetch Mode, based on the use case and the FetchType set as EAGER or LAZY, it will decide how to fetch the data efficiently.

- `FetchType` - When to execute the Query
- `FetchMode` - How to execute the Query

## Schema Migration

We know that JPA creates table using the `@Entity` annotation. But we don't know how does JPA/ORM create the tables, how's it interacting with the database, how's it maintaining the versioning of the schema, etc.

It's like a Blackbox for us. But in enterprise applications, we might want to know how the tables are getting created and what tables are getting created. Maybe we added a new column to the database for a new requirement, but that change didn't work out, and we need to revert the commit for that.

This means that we also need to revert the schema changes. It'll be helpful if we have versioning of the Database schema as well just like we have for Application code using Git. Hence, Schema Migration and Versioning is important.

Some libraries that can help us achieve this are:

1. Flyway (we'll use this in our project)
2. Liquibase

Both serve the same purpose but Flyway is easier to use and is more popular, but Liquibase has more features (most of the time we don't need them).

### Project Structure to maintain the Schema

Example:
- `src/main/resources/db/migration`
  - `V1__Create_Product_Table.sql`
  - `V2__Add_Columns_To_Product_Table.sql`
  - `V3__Add_Columns_To_Product_Table.sql`
  - `V4__Add_Columns_To_Product_Table.sql`

Whenever we make any code change that requires a Database change as well, we need to create a corresponding SQL file. The SQL file should have a version number in the beginning, followed by the changes that need to be made.

When we set our hibernate to update, it creates the table and updates if there are any changes, with the code below:

`spring.jpa.hibernate.ddl-auto=update`

We can change this property to `validate` to validate the schema, but not update it. We can create the tables and make alterations manually with the sb migrations maintained in the `db/migration` folder.