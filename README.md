# Demo Store

Demo website url: https://demostore.top

Management System: https://demostore.top/management

## Project Description

`Demo Store` is a ecommerce system, including online store system and management system. It is using distributed microservices architecture based on Spring Cloud + MyBatis. The online store allows users to view items and create orders. The management system allows administrators to manage orders, items and users.

### System Architecture Diagram

![Architecture Diagram](https://demostore.top/images/structure.png)

### Project File Structure

``` lua
ecommerce
├── sq-eureka -- Service Registry Center
├── sq-gateway -- Spring cloud gateway
├── sq-common -- contains tools and pojo
├── sq-security -- Spring Security for authentication
├── sq-order -- online store order system
├── sq-user -- online store user system
├── sq-item -- online store item system
├── sq-payment -- online store payment system
├── sq-management -- online store management system
├── frontend -- contains frontend files of the oneline store
```

# Project Tech Stacks

Project uses client side rendering and the frontend needs to be deployed separately. 

## Frontend

- Node.js
- Axios
- ES6
- Vue.js
- Vue Router
- Element UI

## Backend

- SpringBoot
- MyBatis
- MySQL
- MyBatis Generator
- Spring Security
- Spring Cloud Gateway
- Eureka
- Feign, Ribbon and Hystrix
- Nginx