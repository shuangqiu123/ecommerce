# Demo Store

Demo website URL: https://demostore.top

Management System: https://demostore.top/management

## Project Description

`Demo Store` is a ecommerce system, including online store system and management system. It is using distributed microservices architecture based on Spring Cloud, Spring Boot, and Vue.js, hosted on AWS Cloud. The online store allows users to view items and create orders. The management system allows administrators to manage orders, items and users.

### System Architecture

![Architecture Diagram](/frontend/src/assets/a.png)

### Project File Structure

``` lua
ecommerce
├── cicd
	├── Jenkinsfile-front -- Jenkins pipeline to build, test, and deploy frontend to S3
	├── Jenkins-back0build-script.sh -- Jenkins Freestyle project to build and deploy docker images to ECR, 
					 -- used as Build Stage in AWS CodePipeline
├── sq-eureka -- Service Registry Center
├── sq-common -- contains tools and pojo
├── sq-security -- Spring Security for authentication
├── sq-order -- online store order system
├── sq-user -- online store user system
├── sq-item -- online store item system
├── sq-payment -- online store payment system
├── sq-management -- online store management system
├── frontend -- contains frontend files of the online store
├── docker-compose.yml -- the docker-compose file to build all microservice images in one command
```

## AWS Cloud Services

- AWS ECS, ECR, EC2, ALB
- AWS S3, CloudFront
- AWS RDS (MySQL)
- AWS Route53
- AWS CodePipeline

## Backend

- Spring Boot
- Spring Security
- Spring Cloud Netflix Eureka Eureka
- Spring MVC
- JWT
- Lombok
- MyBatis
- JUnit
- Mockito

## Frontend

- JavaScript ES6
- Vue.js
- Vue Router
- Axios
- LESS
- HTML5
- Element UI
