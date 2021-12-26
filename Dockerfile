FROM openjdk:11
WORKDIR /workspace/app
VOLUME /tmp
ADD /sq-item/target/sq-item-0.0.1-SNAPSHOT.jar /workspace/app/app.jar
ADD /scripts/script.sh /workspace/app/script.sh
RUN chmod +x /workspace/app/script.sh
ENTRYPOINT ["sh", "-c", "/workspace/app/script.sh"]