#!/bin/sh
java -jar -Dspring.profiles.active=prod \
     -Dspring.datasource.url=$URL \
     -Dspring.datasource.username=$USERNAME \
     -Dspring.datasource.password=$PASSWORD \
     -Djwt.secret=$SECRETE  app.jar