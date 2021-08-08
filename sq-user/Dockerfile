FROM openjdk:11
WORKDIR /workspace/app
VOLUME /tmp
ADD /sq-user/target/sq-user-0.0.1-SNAPSHOT.jar /workspace/app/app.jar
ADD /scripts/script.sh /workspace/app/script.sh
ENTRYPOINT ["sh", "-c", "/app/sript.sh"]