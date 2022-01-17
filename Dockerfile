FROM maven:3.8.4-jdk-11
WORKDIR /workspace/app

COPY /demostore demostore
COPY /pom.xml   pom.xml

RUN mvn clean package -Dmaven.test.skip

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "./demostore/target/demostore-0.0.1-SNAPSHOT.jar"]