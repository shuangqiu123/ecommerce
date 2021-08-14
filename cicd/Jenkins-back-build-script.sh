chmod 777 ./mvnw
./mvnw install
docker-compose build
aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin ${AWS_ECR_CREDENTIALS}
docker tag demostore1_sq-item:latest ${AWS_ECR_CREDENTIALS}/demostore_sq-item:latest
docker tag demostore1_sq-user:latest ${AWS_ECR_CREDENTIALS}/demostore_sq-user:latest
docker tag demostore1_sq-order:latest ${AWS_ECR_CREDENTIALS}/demostore_sq-order:latest
docker tag demostore1_sq-eureka:latest ${AWS_ECR_CREDENTIALS}/demostore_sq-eureka:latest
docker tag demostore1_sq-management:latest ${AWS_ECR_CREDENTIALS}/demostore_sq-management:latest
docker tag demostore1_sq-payment:latest ${AWS_ECR_CREDENTIALS}/demostore_sq-payment:latest
docker push ${AWS_ECR_CREDENTIALS}/demostore_sq-item:latest
docker push ${AWS_ECR_CREDENTIALS}/demostore_sq-management:latest
docker push ${AWS_ECR_CREDENTIALS}/demostore_sq-payment:latest
docker push ${AWS_ECR_CREDENTIALS}/demostore_sq-order:latest
docker push ${AWS_ECR_CREDENTIALS}/demostore_sq-user:latest
docker push ${AWS_ECR_CREDENTIALS}/demostore_sq-eureka:latest