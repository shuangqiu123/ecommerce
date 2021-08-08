FROM openjdk:11
WORKDIR /workspace/app
VOLUME /tmp
ADD /sq-user/target/sq-payment-0.0.1-SNAPSHOT.jar /workspace/app/app.jar
ADD /scripts/script.sh /workspace/app/script.sh
RUN chmod +x /workspace/appscript.sh
ENTRYPOINT ["sh", "-c", "/workspace/app/script.sh"]