## Set up the AWS infrastructure using Terraform

The infrastructure is set up using terraform. RUN ```terraform apply ``` to build the infrastructure and ```terraform destroy``` to destroy the infrastructure.

### Directory

1. ECR: The Elastic Container Registry for the backend application
2. S3AndCloudFront: S3 for static website hosting and CloudFront for CDN
3. ElasticBeanstalk: Custom VPC and Elastic Beanstalk application and environment
4. Redis: a redis server hosted by AWS Elasticache