FROM openjdk:11
WORKDIR /workspace/app
VOLUME /tmp
ADD /sq-eureka/target/sq-eureka-0.0.1-SNAPSHOT.jar /workspace/app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]