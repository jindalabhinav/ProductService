# Product Service

How would a request be sent to the DB and hwo do we handle it:

1. SQL Query
2. Connect to the Database
3. Run the Query
4. Get Row and Parse into required Object
5. Use this Object further

This process is simplified by using ORMs. 

## ORM (Object Relational Mappers)

Provides us an easy way to work with Database and converts data into objects (classes). It can help us to:

- Write queries
- Convert results to objects
- Joins

Using an ORM, we can:

- Convert an entity (like `Product`) into a new Table
  - This saves us from writing the `CREATE TABLE...` SQL queries
- `User - findByName("Abhinav")` - A simple method such as this will get converted into appropriate SQL queries
- etc.

For different kind of Databases like MySQL, Postgres, MongoDb, we have different ORM Drivers which connect to the database. To make use of these different drivers in a loosely coupled way, we make use of JDBC which is an interface to follow a common convention for connecting and interacting with a Database.

> JDBC -> Java Db Connectivity

On top of this, we need a platform or another layer to create the queries, and execute.

Example: `findByName("Abhinav")` --> `SELECT * FROM User WHERE name = "Abhinav"`

This is taken care by the ORMs like Hibernate, MyBatis, etc.

Nopw these ORMs are external to the application and we need a way to make them loosely coupled, and for that we use an Interface called **JPA (Java Persistence API)**.

In a nutshell:

- Client connects to JPA (that abstracts everything else from the Client)
- JPAs (like Spring JPA) interfaces with different ORMs like:
  - Hibernate
  - MyBatis
- Each of these ORMs work with JDBC
- Based upon how many Databases they support, they'll have different drivers like:
  - MySQL Driver
  - Postgres Driver
  - Etc.
- These Drivers will interact with the underlying Database

## Repository Pattern

Usual Flow: Client --> Controller --> Service --> Repository --> Db

Repository layer is also called DAO (Data Access Objects) at some places.

> So what is Repository Pattern?

Code to interact with the Persistence layer should be seperate from the Application layer.

## UUID (Universally Unique Identifier)

- While registering at Scaler, Students have to be added to the Student Tables.
- A primary key is needed for the same.
  - Email? Could be.
    - Disadvantages
      - Space
      - Storage takes more time
      - Indexing will take time
      - Email Id can change as it is a user attribute
    - Not a good Identifier
  - Some Integer/Long as PK? Needs autoincrementing.
    - Disadvantages
      - Overflow
      - If Db is distributed, then PK should be in sync across machines
  - What if my ID is a combination of some details that are unique to a user
    - timestamp + mac_id + ip_addr + serial_no + .....
      - More parameters, lesser collision

This is the concept of a UUID. It's of 128-bit, denoted in a Hexadecimal format.

> Why Hexadecimal?

Using Binary format, we'll need 128 binary digits to represent a UUID, whereas with Hexadecimal, which is 16-base compared to 2-base number system in Binary, 1 hex digit can store the same amount of information as 4 bits (2^4 = 16), thus saving space when stored in Db.

## Inheritence in Database

Scaler has Users having

- ID
- Email
- Pwd

Different type of Users

- Students (extends User)
  - Classes
- Instructors (extends User)
  - Rating
- Mentors (extends User)
  - Sessions
- TAs (extends User)
  - HelpRequests

Each User type has some specific attributes, on top of the common attributes from above. And to create Tables using JPA, we can annotate these classes with `@Entitiy`.

Now Scaler will never have just a normal User, it is always going to be of a specific type. Hence, we don't need a Table for User, but rather for its child classes such as Students and Mentors, which have some common columns from the User class. For this, we'll annotate the User class with `@MappedSuperClass` and not `@Entity`.

> Cons

- I cannot create a Table for the User class as well if there is such a requirement
- How to get all the list of Users
  - Using Joins or Unions

In case I want the Tables for all the classes in the hierarchy, I can use `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`.
