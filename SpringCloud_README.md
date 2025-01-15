# Spring Cloud

To get a resource from a server, we need to know its URL and the Path, to redirect the request to the correct server. A load balancer is responsible for sharing the load among many instance.

This task of routing the request to the correct microservice is done by an **API Gateway**. But balancing requests between the servers is the responsibility of the **Load Balancer**.

Sometimes, there is a single layer that does the work of an `API Gateway + Load Balancer`.

Now the communication between the Product service and the User Service can happen in 2 ways:
- Either it could directly reach out to one of the instances of the User Service
    - Multiple URLs possible
    - Faster
- Or it could call the load balancer and then reach the User service, adding an additional hop
    - Single URL
    - Adds latency

If we can have some Localized LB for User Service, we could improve on the latency by skipping an additional network hop. This is also known as **Service Discovery**.

## Service Registry

Another service that exists whose purpose is to keep note of all servers currently running for every service.

1. Whenever a new server of any service comes up, it will first register itself with service registry.
2. Service Registry also needs to send a heartbeat to all the registered services, so that if a service doesn't respond for let's say 5s, it un-registers that service.

### Service Registry Example Table

| Service Name   | Instance ID       | Host         | Port  | Status    | Last Heartbeat |
|----------------|-------------------|--------------|-------|-----------|----------------|
| user-service   | user-instance-1   | 10.0.1.101   | 8081  | UP        | 30s ago        |
| user-service   | user-instance-2   | 10.0.1.102   | 8081  | UP        | 15s ago        |
| product-service| product-instance-1| 10.0.1.201   | 8082  | UP        | 10s ago        |
| order-service  | order-instance-1  | 10.0.1.301   | 8083  | UP        | 5s ago         |
| order-service  | order-instance-2  | 10.0.1.302   | 8083  | DOWN      | 5min ago       |
| payment-service| payment-instance-1| 10.0.1.401   | 8084  | UP        | 20s ago        |

Spring Cloud has the functionality of Service Discovery. Netflix open-sourced this functionality and added to Spring Cloud.