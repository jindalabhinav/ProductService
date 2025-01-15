# Spring Cloud

Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems (e.g., configuration management, service discovery, circuit breakers, intelligent routing, micro-proxy, control bus, one-time tokens, global locks, leadership election, distributed sessions, cluster state).

## API Gateway and Load Balancer

To get a resource from a server, we need to know its URL and the path to redirect the request to the correct server. A load balancer is responsible for distributing the load among many instances.

The task of routing the request to the correct microservice is done by an **API Gateway**. Balancing requests between the servers is the responsibility of the **Load Balancer**.

Sometimes, there is a single layer that performs the functions of both an `API Gateway` and a `Load Balancer`.

## Communication Between Services

Communication between the Product Service and the User Service can happen in two ways:
- Directly reaching out to one of the instances of the User Service
    - Multiple URLs possible
    - Faster
- Calling the load balancer and then reaching the User Service, adding an additional hop
    - Single URL
    - Adds latency

If we can have a localized load balancer for the User Service, we could improve latency by skipping an additional network hop. This is also known as **Service Discovery**.

## Service Registry

A service registry keeps track of all servers currently running for every service.

1. Whenever a new server of any service comes up, it first registers itself with the service registry.
2. The service registry also needs to send a heartbeat to all the registered services. If a service doesn't respond within a certain time (e.g., 5 seconds), it unregisters that service.

### Service Registry Example Table

| Service Name   | Instance ID       | Host         | Port  | Status    | Last Heartbeat |
|----------------|-------------------|--------------|-------|-----------|----------------|
| user-service   | user-instance-1   | 10.0.1.101   | 8081  | UP        | 30s ago        |
| user-service   | user-instance-2   | 10.0.1.102   | 8081  | UP        | 15s ago        |
| product-service| product-instance-1| 10.0.1.201   | 8082  | UP        | 10s ago        |
| order-service  | order-instance-1  | 10.0.1.301   | 8083  | UP        | 5s ago         |
| order-service  | order-instance-2  | 10.0.1.302   | 8083  | DOWN      | 5min ago       |
| payment-service| payment-instance-1| 10.0.1.401   | 8084  | UP        | 20s ago        |

## Spring Cloud and Netflix OSS

Spring Cloud has the functionality of Service Discovery. Netflix open-sourced this functionality and added it to Spring Cloud. Some of the key Netflix OSS components integrated with Spring Cloud include:
- **Eureka**: Service Discovery
- **Ribbon**: Client-side Load Balancer
- **Hystrix**: Circuit Breaker
- **Zuul**: Edge Server (API Gateway)