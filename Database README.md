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

