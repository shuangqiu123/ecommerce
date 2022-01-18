# Demo Store

[![<ORG_NAME>](https://circleci.com/gh/shuangqiu123/ecommerce.svg?style=shield)](<https://app.circleci.com/pipelines/github/shuangqiu123/ecommerce>)

Website URL: https://store.shuangqiu.blog

Frontend Repo: https://github.com/shuangqiu123/ecommerce-frontend

## Project Description

An online store built using Spring Boot and React.js. The backend is hosted on AWS by AWS Elastic Beanstalk. The infrastructure is built using terraform (Elastic Beanstalk, ECR, S3, IAM Roles and Policies, VPC, and Redis). The backend uses CircleCI as CI/CD tool for deployment.

### Project File Structure

``` lua
ecommerce
├── .circleci
	├── config.yml -- CircleCI configuration file
├── demostore -- backend Spring Boot source code
├── ecommerce-frontend -- git submodule linking to frontend
├── scripts
	├── Dockerrun.aws.json -- JSON file needed for creating Elastic Beanstalk App Version
├── terraform -- Terraform code to build the backend infrastructure
├── Dockerfile -- Build backend docker image
├── pom.xml -- Maven configuration file for parent module
```

## Tech Stacks

### AWS

- AWS Elastic Beanstalk, ECS, ECR, S3, CloudWatch
- AWS RDS (MySQL)
- AWS Route53, ACM

### Backend

- Spring Boot, Spring Framework
- Spring Security with JWT for Authorization and Authentication
- Spring MVC
- Bean Validation
- Lombok
- OAuth2
- MyBatis
- Flyway
- JUnit
- Mockito

### Others

- Google API
- Paypal API
- SendGrid API
