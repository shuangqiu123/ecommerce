#!/bin/sh
java -jar -Dspring.profiles.active=prod \
     -Dspring.datasource.url=$url \
     -Dspring.datasource.username=$username \
     -Dspring.datasource.password=$password \
     -Djwt.secret=$jwtSecret \
     -D.paypal.client.app=$paypalApp \
     -D.paypal.client.secret=$paypalSecret ./demostore/target/demostore-0.0.1-SNAPSHOT.jar