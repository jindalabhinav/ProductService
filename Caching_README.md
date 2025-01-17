# Documentation: Caching using Redis  

## What is a Cache and Why is it Required?  
A **cache** is a temporary storage layer that holds frequently accessed data to reduce the time and resources needed to fetch this data from the primary data source, such as a database.  

### Why Cache Exists:  
- **Speed**: Caching allows data to be retrieved faster as it avoids complex computations or database queries.  
- **Scalability**: Reduces load on the database by serving repeated queries from the cache.  
- **Efficiency**: Improves the response time of services, enhancing user experience.  

## Cache in Product Service  
For the **Product Service** managing product details, a cache can:  
1. Store product details that are frequently accessed.  
2. Reduce the number of database calls when retrieving product information.  
3. Improve overall response time by serving data directly from the cache.  

## How is Cache Better Than Fetching Data From a Database?  
1. **Latency**: Databases often have higher read/write latency compared to cache systems like Redis, which store data in memory.  
2. **Cost**: Fewer database calls reduce infrastructure costs by minimizing database server usage.  
3. **Throughput**: By serving requests from the cache, the system can handle more simultaneous requests without overloading the database.  

## Time Saved by Using Cache Instead of Database  
Fetching data from a cache like Redis (in-memory) typically takes microseconds to milliseconds, while querying a relational database can take milliseconds to seconds depending on query complexity and database load.  

## Local Cache vs Global Cache  
### **Local Cache**  
- A cache stored on the same instance of the application.  
- Examples: In-memory structures like **ConcurrentHashMap** in Java or **MemoryCache** in .NET.  

#### Code Example (using .NET MemoryCache):  
```java
// Create a cache with a 10-minute expiration time
LoadingCache<String, Product> cache = CacheBuilder.newBuilder()
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .build(new CacheLoader<String, Product>() {
            @Override
            public Product load(String key) throws Exception {
                // This method will be called if the key is not found in the cache
                return null;
            }
        });

// Add item to cache
cache.put("product_123", product);

// Retrieve item from cache
Product product = cache.getIfPresent("product_123");
```

#### Pros:  
- Centralized and scalable across multiple servers.  
- Consistent data across all instances.  

#### Cons:  
- Network latency when accessing the cache.  
- Additional complexity in managing Redis.

### **Global Cache**
A shared caching system like Redis, accessible across multiple instances.

#### Code Example (using Redis in C#):

```java
// Create a Jedis connection
Jedis jedis = new Jedis("localhost");
ObjectMapper objectMapper = new ObjectMapper();

// Add item to cache
String productJson = objectMapper.writeValueAsString(product);
jedis.setex("product_123", (int) TimeUnit.MINUTES.toSeconds(10), productJson);

// Retrieve item from cache
String cachedProductJson = jedis.get("product_123");
Product cachedProduct = objectMapper.readValue(cachedProductJson, Product.class);
```

#### Pros:

- Centralized and scalable across multiple servers.
- Consistent data across all instances.

#### Cons:

- Network latency when accessing the cache.
- Additional complexity in managing Redis.

## Browser Cache vs DNS Cache  

### **Browser Cache**  
- Stores web assets (HTML, CSS, JavaScript, images) on the user's device.  
- Improves website loading times by avoiding repeated downloads.  

### **DNS Cache**  
- Stores resolved IP addresses of domain names.  
- Reduces latency in resolving domain names to IPs for subsequent requests.  

## Summary of Pros and Cons  

| Type           | Pros                                          | Cons                                              |  
|----------------|----------------------------------------------|--------------------------------------------------|  
| Local Cache    | Fast, simple, no network overhead             | Not scalable, limited to instance                |  
| Global Cache   | Scalable, centralized, consistent             | Requires network, management overhead            |  
| Browser Cache  | Faster page loads, reduced server load        | Can serve stale data if not updated properly     |  
| DNS Cache      | Reduces latency in domain name resolution     | May cause stale resolutions if DNS records change |  

By integrating Redis for caching in the **Product Service**, we can significantly improve performance and scalability while providing a more responsive user experience.
