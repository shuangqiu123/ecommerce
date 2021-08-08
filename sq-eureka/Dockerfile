FROM openjdk:11
VOLUME /tmp
ADD /target/sq-eureka-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod", "--eureka.instance.hostname==127.0.0.1"]