FROM maven:3.8.4-jdk-11
WORKDIR /workspace/app

COPY /demostore demostore
COPY /pom.xml   pom.xml

COPY /scripts/startup.sh startup.sh
RUN chmod +x startup.sh

RUN mvn clean package -Dmaven.test.skip

ENTRYPOINT ["sh", "-c", "startup.sh"]